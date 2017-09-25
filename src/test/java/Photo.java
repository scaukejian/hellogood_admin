/**
 * Created by kejian on 2017/5/29.
 */
public class Photo {
    private Integer userId;
    private String imgName;//照片名称
    private String realImgName;//真实名字
    private Integer headFlag;// 形象照

    public Photo(Integer userId, String imgName) {
        this.userId = userId;
        this.imgName = imgName;
        this.realImgName = imgName;
        this.headFlag = 0;
    }

    public Integer getHeadFlag() {
        return headFlag;
    }

    public void setHeadFlag(Integer headFlag) {
        this.headFlag = headFlag;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getRealImgName() {
        return realImgName;
    }

    public void setRealImgName(String realImgName) {
        this.realImgName = realImgName;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "userId=" + userId +
                ", imgName='" + imgName + '\'' +
                ", realImgName='" + realImgName + '\'' +
                ", headFlag=" + headFlag +
                '}';
    }
}
