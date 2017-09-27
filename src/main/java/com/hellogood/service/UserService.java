package com.hellogood.service;

import com.google.gson.reflect.TypeToken;
import com.hellogood.constant.Code;
import com.hellogood.constant.EhCacheCode;
import com.hellogood.domain.*;
import com.hellogood.exception.BusinessException;
import com.hellogood.http.vo.*;
import com.hellogood.mapper.BaseDataMapper;
import com.hellogood.mapper.UserMapper;
import com.hellogood.service.redis.RedisCacheManger;
import com.hellogood.utils.*;
import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import org.apache.commons.codec.binary.*;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Base64;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AreaService areaService;

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private UserCacheManager userCacheManager;

    @Autowired
    private BaseDataMapper baseDataMapper;

    @Autowired
    private UserPhotoService userPhotoService;

    private void checkCommon(UserVO vo){
        if (!RegexUtils.isUsername(vo.getUserCode()))
            throw new BusinessException(RegexUtils.USERNAME_MSG);
        if (StringUtils.isBlank(vo.getUserName()))
            throw new BusinessException("操作失败: 姓名不能为空");
        if (vo.getUserName().length() > 32)
            throw new BusinessException("操作失败: 姓名长度不能大于32个字符");
        if (!RegexUtils.isMobileExact(vo.getPhone()))
            throw new BusinessException(RegexUtils.PHONE_MSG);
        if (StringUtils.isBlank(vo.getSex()))
            throw new BusinessException("操作失败: 请选择性别");
        if (vo.getBirthday() == null)
            throw new BusinessException("操作失败: 请输入生日日期");
        if (vo.getAge() != null && !RegexUtils.isPositiveInteger(String.valueOf(vo.getAge())))
            throw new BusinessException("年龄"+RegexUtils.POSITIVE_INTEGER_MSG);
        if (StringUtils.isBlank(vo.getWeixinName()))
            throw new BusinessException("操作失败: 微信不能为空");
        if (vo.getWeixinName().length() > 50)
            throw new BusinessException("操作失败: 微信长度不能大于50个字符");
        if (vo.getHeight() != null && !RegexUtils.isPositiveInteger(String.valueOf(vo.getHeight())))
            throw new BusinessException("身高"+RegexUtils.POSITIVE_INTEGER_MSG);
        //常住城市
        if (StringUtils.isNotBlank(vo.getLiveCity())) {
            if (vo.getLiveCity().length() > 20) throw new BusinessException("操作失败: 常住城市长度不能大于20个字符");
            Area area = areaService.getEqualsCityName(vo.getLiveCity().trim());
            if (area == null) throw new BusinessException("没有查找到对应的城市");
            Province province = provinceService.getByCode(area.getParentId());
            vo.setLiveProvince(province != null ? province.getName() : "");
        }
        vo.setRemark(DataUtil.strToconent(vo.getRemark())); //过滤编辑器的特殊格式
        if (StringUtils.isNotBlank(vo.getRemark()) && vo.getRemark().length() > 255)
            throw new BusinessException("操作失败: 备注长度不能大于255个字符");
        if (StringUtils.isNotBlank(vo.getDegree()) && vo.getDegree().length() > 20)
            throw new BusinessException("操作失败: 学历长度不能大于20个字符");
        if (StringUtils.isNotBlank(vo.getConstellation()) && vo.getConstellation().length() > 10)
            throw new BusinessException("操作失败: 星座长度不能大于10个字符");
        if (StringUtils.isNotBlank(vo.getSchool()) && vo.getSchool().length() > 64)
            throw new BusinessException("操作失败: 学校长度不能大于64个字符");
        if (StringUtils.isNotBlank(vo.getCompany()) && vo.getCompany().length() > 200)
            throw new BusinessException("操作失败: 公司长度不能大于200个字符");
        if (StringUtils.isNotBlank(vo.getJob()) && vo.getJob().length() > 32)
            throw new BusinessException("操作失败: 职位长度不能大于32个字符");
        if (StringUtils.isNotBlank(vo.getCharacteristicSignature()) && vo.getCharacteristicSignature().length() > 255)
            throw new BusinessException("操作失败: 个性签名长度不能大于255个字符");
        if (StringUtils.isNotBlank(vo.getQq()) && vo.getQq().length() > 20)
            throw new BusinessException("操作失败: QQ长度不能大于20个字符");
        if (StringUtils.isNotBlank(vo.getEmail()) && vo.getEmail().length() > 50)
            throw new BusinessException("操作失败: Email长度不能大于50个字符");
    }

    /**
     * 新增
     * @param vo
     */
    public int add(UserVO vo) {
        checkCommon(vo);
        //判断用户名是否重复
        if (getByUserCode(vo.getUserCode()) != null) throw new BusinessException("已存在该账号用户，请重新起名");
        //判断手机号码是否重复
        if (getByPhone(vo.getPhone(), null) != null)  throw new BusinessException("已存在该手机号用户，请用新手机号码");
        User domain = new User();
        vo.vo2Domain(domain);
        domain.setCreateTime(new Date());
        domain.setUpdateTime(new Date());
        domain.setValidStatus(Code.STATUS_VALID);
        userMapper.insert(domain);
        if (vo.getHeadPhotoId() != null && vo.getHeadPhotoId() > 0) {
            UserPhoto userPhoto = userPhotoService.getUserPhoto(vo.getHeadPhotoId());
            if (userPhoto != null) {
                userPhoto.setUserId(domain.getId());
                userPhotoService.update(userPhoto);
            }
        }
        return domain.getId();
    }

    /**
     * 删除
     * @param ids
     */
    public void delete(String ids) {
        if (StringUtils.isBlank(ids)) throw new BusinessException("请选择要删除的记录");
        String[] idStrArr = ids.split(",");
        for (String idStr : idStrArr) {
            Integer userId = Integer.parseInt(idStr);
            User user = userMapper.selectByPrimaryKey(userId);
            if (user == null) continue;
            user.setValidStatus(Code.STATUS_INVALID);
            userMapper.updateByPrimaryKeySelective(user);
        }
    }

    /**
     * 修改
     * @param vo
     */
    @CacheEvict(value = EhCacheCode.CACHE_TYPE_USER, key = "#id")
    public void update(UserVO vo) {
        checkCommon(vo);
        User domain = new User();
        vo.vo2Domain(domain);
        domain.setUpdateTime(new Date());
        userMapper.updateByPrimaryKeySelective(domain);
    }


    /**
     * 修改
     * @param vo
     */
    @CacheEvict(value = EhCacheCode.CACHE_TYPE_USER, key = "#id")
    public void updateSelective(UserVO vo) {
        checkCommon(vo);
        //判断是否更改了账号
        User user = userMapper.selectByPrimaryKey(vo.getId());
        if (user == null) throw new BusinessException("更新失败");
        if (!StringUtils.equals(user.getUserCode(), vo.getUserCode())) throw new BusinessException("不能更改用户的账号");
        if (getByPhone(vo.getPhone(), vo.getId()) != null) throw new BusinessException("已存在该手机号用户，请用新手机号码");
        User domain = new User();
        vo.vo2Domain(domain);
        domain.setUpdateTime(new Date());
        userMapper.updateByPrimaryKeySelective(domain);
        //更新缓存
        userCacheManager.updateBaseInfo(vo.getId(), vo.getUserCode(), vo.getUserName(), vo.getSex(), Long.valueOf(vo.getBirthday().getTime()).toString());
    }

    /**
     * 获取基本信息
     *
     * @param id
     * @return
     */
    @Cacheable(value = EhCacheCode.CACHE_TYPE_USER, key = "#id")
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public UserVO getUserBaseInfoIncludePhone(Integer id) {
        User domain = userMapper.selectByPrimaryKey(id);
        UserVO vo = new UserVO();
        vo.domain2Vo(domain);
        return vo;
    }

    /**
     * 获取基本信息排除电话号码
     * @param id
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public UserVO getUserBaseInfo(Integer id) {
        UserVO vo = getUserBaseInfoIncludePhone(id);
        /**
         * 过滤重要信息
         */
        vo.setPhone("");
        vo.setWeixinName("");
        return vo;
    }

    /**
     * 获取基本信息-不缓存记录
     *
     * @param id
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public UserVO getUserBaseInfoNonCache(Integer id) {
        User domain = userMapper.selectByPrimaryKey(id);
        UserVO vo = new UserVO();
        vo.domain2Vo(domain);
        return vo;
    }
    
    /**
     * 获取数据
     *
     * @param id
     * @return
     */
    public User getUser(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    /**
     * 获取用户详情
     *
     * @param id
     * @return
     */
//	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public UserVO get(Integer id) {
        UserVO vo = new UserVO();
        if (id == null) {
            return vo;
        }
        User domain = userMapper.selectByPrimaryKey(id);
        vo.domain2Vo(domain);
        userDetailSupplement(vo);
        return vo;
    }
    /**
     * 用户详情补全
     * @param vo
     */
    void userDetailSupplement(UserVO vo){
        vo.setHeadPhoto(userPhotoService.getHeadPhotoByUserId(vo.getId()));//头像
        vo.setHeadPhotoName(userPhotoService.getCacheAvatar(vo.getId()));//获取缓存中的头像名称
    }
    /**
     * 获取用户简要
     *
     * @param id
     * @return
     */
    public UserVO getSimpleInfo(Integer id) {
        UserVO vo = new UserVO();
        if (id == null) {
            return vo;
        }
        User domain = userMapper.selectByPrimaryKey(id);
        vo.domain2Vo(domain);
        return vo;
    }

    /**
     * 分页查询
     * @param userVO
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public List<UserVO> pageJson(UserVO userVO, int page, int pageSize) {
        logger.info("用户分页查询开始..");
        Integer start = page <= 1 ? 0 : (page - 1) * pageSize;
        userVO.setPage(start);
        userVO.setPageSize(pageSize);
        if (userVO.getMinAge() != null) {
            Date birthday = DateUtil.getBirthDay(userVO.getMinAge());
            userVO.setBirthDayBeginTime(DateUtil.stringToDate(DateUtil.dateToYMD(birthday) + " 00:00:00"));
        }
        if (userVO.getMaxAge() != null) {
            Date birthday_end = DateUtil.getBirthDay(userVO.getMaxAge() + 1);
            userVO.setBirthDayEndTime(DateUtil.stringToDate(DateUtil.dateToYMD(birthday_end) + " 23:59:59"));
        }
        long currentTime = System.currentTimeMillis();
        List<User> userList = userMapper.listUserBySearch(userVO);
        logger.info("用户分页查询[SQL] 耗时..." + (System.currentTimeMillis() - currentTime));
        currentTime = System.currentTimeMillis();
        List<UserVO> userVOList = new ArrayList<>();
        for (User user : userList) {
            UserVO vo = new UserVO();
            vo.domain2Vo(user);
            if (user.getBirthday() != null) {
                vo.setAge(DateUtil.getAge(vo.getBirthday()));
            }
            supplement(vo);
            userVOList.add(vo);
        }
        logger.info("用户分页查询[完善信息] 耗时..." + (System.currentTimeMillis() - currentTime));
        return userVOList;
    }
    /**
     * 完善用户信息
     * @param vo
     */
    private void supplement(UserVO vo) {
        long currentTime = System.currentTimeMillis();
        //获取头像
        currentTime = System.currentTimeMillis();
        logger.info("用户分页查询[完善信息-获取头像] 耗时..." + (System.currentTimeMillis() - currentTime));
        //获取备注
        currentTime = System.currentTimeMillis();
        logger.info("用户分页查询[完善信息-获取备注] 耗时..." + (System.currentTimeMillis() - currentTime));

    }

    private UserExample generatePageQueryUserExampleAndUserIds(UserVO queryVo) {

        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotBlank(queryVo.getDegree())) {
            criteria.andDegreeEqualTo(queryVo.getDegree());
        }
        if (queryVo.getMinHeight() != null) {
            criteria.andHeightGreaterThanOrEqualTo(queryVo.getMinHeight());
        }
        if (queryVo.getMaxHeight() != null) {
            criteria.andHeightLessThanOrEqualTo(queryVo.getMaxHeight());
        }

        if (queryVo.getMinAge() != null) {
            Date birthday = DateUtil.getBirthDay(queryVo.getMinAge());
            criteria.andBirthdayLessThanOrEqualTo(DateUtil
                    .stringToDate(DateUtil.dateToYMD(birthday) + " 00:00:00"));
        }

        if (queryVo.getMaxAge() != null) {
            Date birthday = DateUtil.getBirthDay(queryVo.getMaxAge() + 1);
            criteria.andBirthdayGreaterThan(DateUtil
                    .stringToDate(DateUtil.dateToYMD(birthday) + " 23:59:59"));
        }
        // 条件查询
        if (StringUtils.isNotBlank(queryVo.getUserName())) {
            // 模糊查询
            criteria.andUserNameLike(MessageFormat.format("%{0}%",
                    queryVo.getUserName()));
        }
        if (StringUtils.isNotBlank(queryVo.getUserCode())) {
            criteria.andUserCodeLike(MessageFormat.format("%{0}%",
                    queryVo.getUserCode()));
        }
        if (StringUtils.isNotBlank(queryVo.getPhone())) {
            criteria.andPhoneLike(MessageFormat.format("%{0}%",
                    queryVo.getPhone()));
        }
        if (StringUtils.isNotBlank(queryVo.getSex())) {
            criteria.andSexEqualTo(queryVo.getSex());
        }
        if (StringUtils.isNotBlank(queryVo.getLiveCity())) {
            criteria.andLiveCityLike(MessageFormat.format("%{0}%",
                    queryVo.getLiveCity()));
        }
        if(StringUtils.isNotBlank(queryVo.getMaritalStatus())){
            criteria.andMaritalStatusEqualTo(queryVo.getMaritalStatus());
        }
        if(queryVo.getCityList() != null){
            criteria.andLiveCityIn(queryVo.getCityList());
        }
        return example;
    }

    /**
     * 生成分页查询条件模板
     *
     * @param queryVo
     * @return
     */
    private UserExample generatePageQueryUserExample(UserVO queryVo) {
        UserExample example = new UserExample();
        // 条件查询
        UserExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(queryVo.getUserName())) {
            // 模糊查询
            criteria.andUserNameLike(MessageFormat.format("%{0}%",
                    queryVo.getUserName()));
        }
        if (StringUtils.isNotBlank(queryVo.getUserCode())) {
            criteria.andUserCodeLike(MessageFormat.format("%{0}%",
                    queryVo.getUserCode()));
        }
        if (StringUtils.isNotBlank(queryVo.getPhone())) {
            criteria.andPhoneLike(MessageFormat.format("%{0}%",
                    queryVo.getPhone()));
        }
        if (StringUtils.isNotBlank(queryVo.getSex())) {
            criteria.andSexEqualTo(queryVo.getSex());
        }
        if (StringUtils.isNotBlank(queryVo.getLiveCity())) {
            criteria.andLiveCityLike(MessageFormat.format("%{0}%",
                    queryVo.getLiveCity()));
        }

        return example;
    }

    /**
     * @param inputStream
     */
    public void excelImport(InputStream inputStream) {
        List<Map<String, String>> excelData = readExcelData(inputStream);
        if (excelData == null || excelData.isEmpty()) {
            throw new BusinessException("导入EXCEL不能为空!");
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (Map<String, String> map : excelData) {
            String userName = map.get("userName");
            if (StringUtils.isBlank(userName)) {
                throw new BusinessException("姓名不能为空");
            }
            User user = new User();
            user.setUserName(map.get("userName"));
            user.setSex(map.get("sex"));
            user.setHeight(new BigDecimal(map.get("height")).intValue());
            user.setWeight(new BigDecimal(map.get("weight")).intValue());
            user.setDegree(map.get("degree"));
            if (StringUtils.isNotBlank(map.get("birthday"))) {
                try {
                    user.setBirthday(dateFormat.parse(map.get("birthday")));
                } catch (Exception e) {
                    throw new BusinessException(userName + "生日格式必须为yyyy-MM-dd");
                }
            }
            user.setNativePlace(map.get("nativePlace"));
            user.setNationality(map.get("nationality"));
            user.setMaritalStatus(map.get("marryStatus"));
            user.setJob(map.get("job"));
            user.setLiveCity(map.get("liveCity"));
            if (StringUtils.isNotBlank(map.get("registerTime"))) {
                try {
                    user.setCreateTime(dateFormat.parse(map.get("createTime")));
                } catch (Exception e) {
                    throw new BusinessException(userName
                            + "注册时间格式必须为yyyy-MM-dd");
                }
            }
            userMapper.insert(user);
        }
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    private List<Map<String, String>> readExcelData(InputStream inputStream) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Workbook workbook = null;
        Sheet sheet = null;
        Row row = null;
        try {
            // 2007以前
            workbook = new XSSFWorkbook(inputStream);
        } catch (Exception e) {
            // 2007
            try {
                workbook = new HSSFWorkbook(new POIFSFileSystem(inputStream));
            } catch (Exception e2) {
                throw new BusinessException("导入的Excel文件只能为office2007或更早版本!");
            }
        }
        String[] cellNames = new String[]{"姓名", "性别", "身高", "体重", "学历", "生日",
                "籍贯", "民族", "婚姻状况", "工作","常驻城市",  "注册时间"};
        for (int index = 0; index < workbook.getNumberOfSheets(); index++) {
            sheet = workbook.getSheetAt(index);
            // 从第3行开始读取. 1,2行为标题
            for (int rowIndex = 2; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Map<String, String> rowData = new HashMap<String, String>();
                row = sheet.getRow(rowIndex);
                if (row == null) {
                    continue;
                }
                // 姓名, 性别, 身高, 体重, 学历, 生日, 籍贯, 民族, 婚姻状况, 家庭, 所属行业, 工作, 资产, 常驻城市,
                // 个人状态, 购房情况, 购车情况, 注册时间
                String[] cellTitles = new String[]{"userName", "sex",
                        "height", "weight", "degree", "birthday",
                        "nativePlace", "nationality", "marryStatus ",
                        "job", "liveCity","registerTime"};
                int titleIndex = 0;
                for (String title : cellTitles) {
                    rowData.put(title,
                            ExcelImport.getValue(row, titleIndex, cellNames));// 姓名
                    titleIndex++;
                }
                list.add(rowData);
            }
        }
        return list;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public List<Integer> getUserIdsByUserCode(String userCode) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(userCode)) {
            criteria.andUserCodeLike(MessageFormat.format("%{0}%", userCode));
        }
        List<Integer> idList = new ArrayList<>();
        List<User> userList = userMapper.selectByExample(example);
        for (User user : userList) {
            idList.add(user.getId());
        }
        return idList;
    }
    
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public List<Integer> getUserIdsByUserCodeUnique(String userCode) {
    	UserExample example = new UserExample();
    	UserExample.Criteria criteria = example.createCriteria();
    	criteria.andUserCodeEqualTo(userCode);
    	List<Integer> idList = new ArrayList<>();
    	List<User> userList = userMapper.selectByExample(example);
    	for (User user : userList) {
    		idList.add(user.getId());
    	}
    	return idList;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public List<Integer> getUserIdsByUserName(String userName) {
        List<Integer> idList = new ArrayList<Integer>();
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(userName)) {
            criteria.andUserNameLike((MessageFormat.format("%{0}%", userName)));
        }
        List<User> userList = userMapper.selectByExample(example);
        if (userList != null && userList.size() > 0) {
            for (User user : userList) {
                idList.add(user.getId());
            }
        }
        return idList;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public List<Integer> getUserIds(UserVO userVO) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(userVO.getUserName())) {
            criteria.andUserNameLike(MessageFormat.format("%{0}%",
                    userVO.getUserName()));
        }
        if (StringUtils.isNotBlank(userVO.getUserCode())) {
            criteria.andUserCodeLike(MessageFormat.format("%{0}%",
                    userVO.getUserCode()));
        }
        if (StringUtils.isNotBlank(userVO.getPhone())) {
            criteria.andPhoneLike(MessageFormat.format("%{0}%",
                    userVO.getPhone()));
        }
        if (StringUtils.isNotBlank(userVO.getSex())) {
            criteria.andSexEqualTo(userVO.getSex());
        }
        List<Integer> idList = new ArrayList<Integer>();
        List<User> userList = userMapper.selectByExample(example);
        for (User user : userList) {
            idList.add(user.getId());
        }
        return idList;
    }

    public Integer getSearchTotal(UserVO userVO) {
        if (userVO.getMinAge() != null) {
            Date birthday = DateUtil.getBirthDay(userVO.getMinAge());
            userVO.setBirthDayBeginTime(DateUtil.stringToDate(DateUtil.dateToYMD(birthday) + " 00:00:00"));
        }
        if (userVO.getMaxAge() != null) {
            Date birthday_end = DateUtil.getBirthDay(userVO.getMaxAge() + 1);
            userVO.setBirthDayEndTime(DateUtil.stringToDate(DateUtil.dateToYMD(birthday_end) + " 23:59:59"));
        }
        return userMapper.getSearchTotal(userVO);
    }


    public List<UserVO> getUserByUserCode(String userCode) {
        PageHelper.startPage(1, 7);
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        List<UserVO> vos = new ArrayList<UserVO>();
        if (StringUtils.isNotBlank(userCode)) {
            criteria.andUserCodeLike(MessageFormat.format("%{0}%", userCode));
        }
        List<User> users = userMapper.selectByExample(example);
        for (User user : users) {
            UserVO vo = new UserVO();
            vo.domain2Vo(user);
            vos.add(vo);
        }
        return vos;
    }

    /**
     * 通过userCode查找对应用户
     * @param userCode
     * @return
     */
    public User getByUserCode(String userCode) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUserCodeEqualTo(userCode);
        List<User> users = userMapper.selectByExample(example);
        if (!users.isEmpty()) {
        	return users.get(0);
        }
        return null;
    }

    /**
     * 通过手机号码查找对应用户
     * @param phone
     * @return
     */
    public User getByPhone(String phone, Integer userId) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andPhoneEqualTo(phone);
        if (userId != null) criteria.andIdNotEqualTo(userId);
        List<User> users = userMapper.selectByExample(example);
        if (!users.isEmpty()) {
        	return users.get(0);
        }
        return null;
    }

    /**
     * 通过用户列表获取用户易悦号列表
     * @param list
     * @return
     */
    public String[] getUserCodesByUserList(List<User> list) {
        String[] userCodes = null;
        if (list != null && list.size() > 0) {
            userCodes = new String[list.size()];
            for (int i = 0; i < userCodes.length; i++) {
                userCodes[i] = list.get(i).getUserCode();
            }
        }
        return userCodes;
    }

    public List<Integer> getAllUserIds() {
        List<User> list = userMapper.selectByExample(new UserExample());
        return list.stream().map(user -> user.getId()).collect(Collectors.toList());
    }


    /**
     * 根据用户易悦号集合查找对应用户
     * @param userCodeList
     * @return
     */
    public List<User> getUserListByUserCodes(List<String> userCodeList) {
        if (userCodeList == null || userCodeList.isEmpty())
            return null;
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUserCodeIn(userCodeList);
        return userMapper.selectByExample(example);
    }

}
