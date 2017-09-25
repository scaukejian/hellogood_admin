package com.hellogood.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LoginExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public LoginExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Integer value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Integer value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Integer value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Integer value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Integer> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Integer> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Integer value1, Integer value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNull() {
            addCriterion("phone is null");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNotNull() {
            addCriterion("phone is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneEqualTo(String value) {
            addCriterion("phone =", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotEqualTo(String value) {
            addCriterion("phone <>", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThan(String value) {
            addCriterion("phone >", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("phone >=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThan(String value) {
            addCriterion("phone <", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThanOrEqualTo(String value) {
            addCriterion("phone <=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLike(String value) {
            addCriterion("phone like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotLike(String value) {
            addCriterion("phone not like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneIn(List<String> values) {
            addCriterion("phone in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotIn(List<String> values) {
            addCriterion("phone not in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneBetween(String value1, String value2) {
            addCriterion("phone between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotBetween(String value1, String value2) {
            addCriterion("phone not between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andBlacklistIsNull() {
            addCriterion("blacklist is null");
            return (Criteria) this;
        }

        public Criteria andBlacklistIsNotNull() {
            addCriterion("blacklist is not null");
            return (Criteria) this;
        }

        public Criteria andBlacklistEqualTo(Integer value) {
            addCriterion("blacklist =", value, "blacklist");
            return (Criteria) this;
        }

        public Criteria andBlacklistNotEqualTo(Integer value) {
            addCriterion("blacklist <>", value, "blacklist");
            return (Criteria) this;
        }

        public Criteria andBlacklistGreaterThan(Integer value) {
            addCriterion("blacklist >", value, "blacklist");
            return (Criteria) this;
        }

        public Criteria andBlacklistGreaterThanOrEqualTo(Integer value) {
            addCriterion("blacklist >=", value, "blacklist");
            return (Criteria) this;
        }

        public Criteria andBlacklistLessThan(Integer value) {
            addCriterion("blacklist <", value, "blacklist");
            return (Criteria) this;
        }

        public Criteria andBlacklistLessThanOrEqualTo(Integer value) {
            addCriterion("blacklist <=", value, "blacklist");
            return (Criteria) this;
        }

        public Criteria andBlacklistIn(List<Integer> values) {
            addCriterion("blacklist in", values, "blacklist");
            return (Criteria) this;
        }

        public Criteria andBlacklistNotIn(List<Integer> values) {
            addCriterion("blacklist not in", values, "blacklist");
            return (Criteria) this;
        }

        public Criteria andBlacklistBetween(Integer value1, Integer value2) {
            addCriterion("blacklist between", value1, value2, "blacklist");
            return (Criteria) this;
        }

        public Criteria andBlacklistNotBetween(Integer value1, Integer value2) {
            addCriterion("blacklist not between", value1, value2, "blacklist");
            return (Criteria) this;
        }

        public Criteria andPhoneClientIsNull() {
            addCriterion("phone_client is null");
            return (Criteria) this;
        }

        public Criteria andPhoneClientIsNotNull() {
            addCriterion("phone_client is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneClientEqualTo(String value) {
            addCriterion("phone_client =", value, "phoneClient");
            return (Criteria) this;
        }

        public Criteria andPhoneClientNotEqualTo(String value) {
            addCriterion("phone_client <>", value, "phoneClient");
            return (Criteria) this;
        }

        public Criteria andPhoneClientGreaterThan(String value) {
            addCriterion("phone_client >", value, "phoneClient");
            return (Criteria) this;
        }

        public Criteria andPhoneClientGreaterThanOrEqualTo(String value) {
            addCriterion("phone_client >=", value, "phoneClient");
            return (Criteria) this;
        }

        public Criteria andPhoneClientLessThan(String value) {
            addCriterion("phone_client <", value, "phoneClient");
            return (Criteria) this;
        }

        public Criteria andPhoneClientLessThanOrEqualTo(String value) {
            addCriterion("phone_client <=", value, "phoneClient");
            return (Criteria) this;
        }

        public Criteria andPhoneClientLike(String value) {
            addCriterion("phone_client like", value, "phoneClient");
            return (Criteria) this;
        }

        public Criteria andPhoneClientNotLike(String value) {
            addCriterion("phone_client not like", value, "phoneClient");
            return (Criteria) this;
        }

        public Criteria andPhoneClientIn(List<String> values) {
            addCriterion("phone_client in", values, "phoneClient");
            return (Criteria) this;
        }

        public Criteria andPhoneClientNotIn(List<String> values) {
            addCriterion("phone_client not in", values, "phoneClient");
            return (Criteria) this;
        }

        public Criteria andPhoneClientBetween(String value1, String value2) {
            addCriterion("phone_client between", value1, value2, "phoneClient");
            return (Criteria) this;
        }

        public Criteria andPhoneClientNotBetween(String value1, String value2) {
            addCriterion("phone_client not between", value1, value2, "phoneClient");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNull() {
            addCriterion("password is null");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNotNull() {
            addCriterion("password is not null");
            return (Criteria) this;
        }

        public Criteria andPasswordEqualTo(String value) {
            addCriterion("password =", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotEqualTo(String value) {
            addCriterion("password <>", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThan(String value) {
            addCriterion("password >", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThanOrEqualTo(String value) {
            addCriterion("password >=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThan(String value) {
            addCriterion("password <", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThanOrEqualTo(String value) {
            addCriterion("password <=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLike(String value) {
            addCriterion("password like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotLike(String value) {
            addCriterion("password not like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordIn(List<String> values) {
            addCriterion("password in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotIn(List<String> values) {
            addCriterion("password not in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordBetween(String value1, String value2) {
            addCriterion("password between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotBetween(String value1, String value2) {
            addCriterion("password not between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andLoginIpIsNull() {
            addCriterion("login_ip is null");
            return (Criteria) this;
        }

        public Criteria andLoginIpIsNotNull() {
            addCriterion("login_ip is not null");
            return (Criteria) this;
        }

        public Criteria andLoginIpEqualTo(String value) {
            addCriterion("login_ip =", value, "loginIp");
            return (Criteria) this;
        }

        public Criteria andLoginIpNotEqualTo(String value) {
            addCriterion("login_ip <>", value, "loginIp");
            return (Criteria) this;
        }

        public Criteria andLoginIpGreaterThan(String value) {
            addCriterion("login_ip >", value, "loginIp");
            return (Criteria) this;
        }

        public Criteria andLoginIpGreaterThanOrEqualTo(String value) {
            addCriterion("login_ip >=", value, "loginIp");
            return (Criteria) this;
        }

        public Criteria andLoginIpLessThan(String value) {
            addCriterion("login_ip <", value, "loginIp");
            return (Criteria) this;
        }

        public Criteria andLoginIpLessThanOrEqualTo(String value) {
            addCriterion("login_ip <=", value, "loginIp");
            return (Criteria) this;
        }

        public Criteria andLoginIpLike(String value) {
            addCriterion("login_ip like", value, "loginIp");
            return (Criteria) this;
        }

        public Criteria andLoginIpNotLike(String value) {
            addCriterion("login_ip not like", value, "loginIp");
            return (Criteria) this;
        }

        public Criteria andLoginIpIn(List<String> values) {
            addCriterion("login_ip in", values, "loginIp");
            return (Criteria) this;
        }

        public Criteria andLoginIpNotIn(List<String> values) {
            addCriterion("login_ip not in", values, "loginIp");
            return (Criteria) this;
        }

        public Criteria andLoginIpBetween(String value1, String value2) {
            addCriterion("login_ip between", value1, value2, "loginIp");
            return (Criteria) this;
        }

        public Criteria andLoginIpNotBetween(String value1, String value2) {
            addCriterion("login_ip not between", value1, value2, "loginIp");
            return (Criteria) this;
        }

        public Criteria andLoginAddrIsNull() {
            addCriterion("login_addr is null");
            return (Criteria) this;
        }

        public Criteria andLoginAddrIsNotNull() {
            addCriterion("login_addr is not null");
            return (Criteria) this;
        }

        public Criteria andLoginAddrEqualTo(String value) {
            addCriterion("login_addr =", value, "loginAddr");
            return (Criteria) this;
        }

        public Criteria andLoginAddrNotEqualTo(String value) {
            addCriterion("login_addr <>", value, "loginAddr");
            return (Criteria) this;
        }

        public Criteria andLoginAddrGreaterThan(String value) {
            addCriterion("login_addr >", value, "loginAddr");
            return (Criteria) this;
        }

        public Criteria andLoginAddrGreaterThanOrEqualTo(String value) {
            addCriterion("login_addr >=", value, "loginAddr");
            return (Criteria) this;
        }

        public Criteria andLoginAddrLessThan(String value) {
            addCriterion("login_addr <", value, "loginAddr");
            return (Criteria) this;
        }

        public Criteria andLoginAddrLessThanOrEqualTo(String value) {
            addCriterion("login_addr <=", value, "loginAddr");
            return (Criteria) this;
        }

        public Criteria andLoginAddrLike(String value) {
            addCriterion("login_addr like", value, "loginAddr");
            return (Criteria) this;
        }

        public Criteria andLoginAddrNotLike(String value) {
            addCriterion("login_addr not like", value, "loginAddr");
            return (Criteria) this;
        }

        public Criteria andLoginAddrIn(List<String> values) {
            addCriterion("login_addr in", values, "loginAddr");
            return (Criteria) this;
        }

        public Criteria andLoginAddrNotIn(List<String> values) {
            addCriterion("login_addr not in", values, "loginAddr");
            return (Criteria) this;
        }

        public Criteria andLoginAddrBetween(String value1, String value2) {
            addCriterion("login_addr between", value1, value2, "loginAddr");
            return (Criteria) this;
        }

        public Criteria andLoginAddrNotBetween(String value1, String value2) {
            addCriterion("login_addr not between", value1, value2, "loginAddr");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andLastLogintimeIsNull() {
            addCriterion("last_logintime is null");
            return (Criteria) this;
        }

        public Criteria andLastLogintimeIsNotNull() {
            addCriterion("last_logintime is not null");
            return (Criteria) this;
        }

        public Criteria andLastLogintimeEqualTo(Date value) {
            addCriterion("last_logintime =", value, "lastLogintime");
            return (Criteria) this;
        }

        public Criteria andLastLogintimeNotEqualTo(Date value) {
            addCriterion("last_logintime <>", value, "lastLogintime");
            return (Criteria) this;
        }

        public Criteria andLastLogintimeGreaterThan(Date value) {
            addCriterion("last_logintime >", value, "lastLogintime");
            return (Criteria) this;
        }

        public Criteria andLastLogintimeGreaterThanOrEqualTo(Date value) {
            addCriterion("last_logintime >=", value, "lastLogintime");
            return (Criteria) this;
        }

        public Criteria andLastLogintimeLessThan(Date value) {
            addCriterion("last_logintime <", value, "lastLogintime");
            return (Criteria) this;
        }

        public Criteria andLastLogintimeLessThanOrEqualTo(Date value) {
            addCriterion("last_logintime <=", value, "lastLogintime");
            return (Criteria) this;
        }

        public Criteria andLastLogintimeIn(List<Date> values) {
            addCriterion("last_logintime in", values, "lastLogintime");
            return (Criteria) this;
        }

        public Criteria andLastLogintimeNotIn(List<Date> values) {
            addCriterion("last_logintime not in", values, "lastLogintime");
            return (Criteria) this;
        }

        public Criteria andLastLogintimeBetween(Date value1, Date value2) {
            addCriterion("last_logintime between", value1, value2, "lastLogintime");
            return (Criteria) this;
        }

        public Criteria andLastLogintimeNotBetween(Date value1, Date value2) {
            addCriterion("last_logintime not between", value1, value2, "lastLogintime");
            return (Criteria) this;
        }

        public Criteria andLastLogouttimeIsNull() {
            addCriterion("last_logouttime is null");
            return (Criteria) this;
        }

        public Criteria andLastLogouttimeIsNotNull() {
            addCriterion("last_logouttime is not null");
            return (Criteria) this;
        }

        public Criteria andLastLogouttimeEqualTo(Date value) {
            addCriterion("last_logouttime =", value, "lastLogouttime");
            return (Criteria) this;
        }

        public Criteria andLastLogouttimeNotEqualTo(Date value) {
            addCriterion("last_logouttime <>", value, "lastLogouttime");
            return (Criteria) this;
        }

        public Criteria andLastLogouttimeGreaterThan(Date value) {
            addCriterion("last_logouttime >", value, "lastLogouttime");
            return (Criteria) this;
        }

        public Criteria andLastLogouttimeGreaterThanOrEqualTo(Date value) {
            addCriterion("last_logouttime >=", value, "lastLogouttime");
            return (Criteria) this;
        }

        public Criteria andLastLogouttimeLessThan(Date value) {
            addCriterion("last_logouttime <", value, "lastLogouttime");
            return (Criteria) this;
        }

        public Criteria andLastLogouttimeLessThanOrEqualTo(Date value) {
            addCriterion("last_logouttime <=", value, "lastLogouttime");
            return (Criteria) this;
        }

        public Criteria andLastLogouttimeIn(List<Date> values) {
            addCriterion("last_logouttime in", values, "lastLogouttime");
            return (Criteria) this;
        }

        public Criteria andLastLogouttimeNotIn(List<Date> values) {
            addCriterion("last_logouttime not in", values, "lastLogouttime");
            return (Criteria) this;
        }

        public Criteria andLastLogouttimeBetween(Date value1, Date value2) {
            addCriterion("last_logouttime between", value1, value2, "lastLogouttime");
            return (Criteria) this;
        }

        public Criteria andLastLogouttimeNotBetween(Date value1, Date value2) {
            addCriterion("last_logouttime not between", value1, value2, "lastLogouttime");
            return (Criteria) this;
        }

        public Criteria andApkVersionIsNull() {
            addCriterion("apk_version is null");
            return (Criteria) this;
        }

        public Criteria andApkVersionIsNotNull() {
            addCriterion("apk_version is not null");
            return (Criteria) this;
        }

        public Criteria andApkVersionEqualTo(String value) {
            addCriterion("apk_version =", value, "apkVersion");
            return (Criteria) this;
        }

        public Criteria andApkVersionNotEqualTo(String value) {
            addCriterion("apk_version <>", value, "apkVersion");
            return (Criteria) this;
        }

        public Criteria andApkVersionGreaterThan(String value) {
            addCriterion("apk_version >", value, "apkVersion");
            return (Criteria) this;
        }

        public Criteria andApkVersionGreaterThanOrEqualTo(String value) {
            addCriterion("apk_version >=", value, "apkVersion");
            return (Criteria) this;
        }

        public Criteria andApkVersionLessThan(String value) {
            addCriterion("apk_version <", value, "apkVersion");
            return (Criteria) this;
        }

        public Criteria andApkVersionLessThanOrEqualTo(String value) {
            addCriterion("apk_version <=", value, "apkVersion");
            return (Criteria) this;
        }

        public Criteria andApkVersionLike(String value) {
            addCriterion("apk_version like", value, "apkVersion");
            return (Criteria) this;
        }

        public Criteria andApkVersionNotLike(String value) {
            addCriterion("apk_version not like", value, "apkVersion");
            return (Criteria) this;
        }

        public Criteria andApkVersionIn(List<String> values) {
            addCriterion("apk_version in", values, "apkVersion");
            return (Criteria) this;
        }

        public Criteria andApkVersionNotIn(List<String> values) {
            addCriterion("apk_version not in", values, "apkVersion");
            return (Criteria) this;
        }

        public Criteria andApkVersionBetween(String value1, String value2) {
            addCriterion("apk_version between", value1, value2, "apkVersion");
            return (Criteria) this;
        }

        public Criteria andApkVersionNotBetween(String value1, String value2) {
            addCriterion("apk_version not between", value1, value2, "apkVersion");
            return (Criteria) this;
        }

        public Criteria andClientInfoIsNull() {
            addCriterion("client_info is null");
            return (Criteria) this;
        }

        public Criteria andClientInfoIsNotNull() {
            addCriterion("client_info is not null");
            return (Criteria) this;
        }

        public Criteria andClientInfoEqualTo(String value) {
            addCriterion("client_info =", value, "clientInfo");
            return (Criteria) this;
        }

        public Criteria andClientInfoNotEqualTo(String value) {
            addCriterion("client_info <>", value, "clientInfo");
            return (Criteria) this;
        }

        public Criteria andClientInfoGreaterThan(String value) {
            addCriterion("client_info >", value, "clientInfo");
            return (Criteria) this;
        }

        public Criteria andClientInfoGreaterThanOrEqualTo(String value) {
            addCriterion("client_info >=", value, "clientInfo");
            return (Criteria) this;
        }

        public Criteria andClientInfoLessThan(String value) {
            addCriterion("client_info <", value, "clientInfo");
            return (Criteria) this;
        }

        public Criteria andClientInfoLessThanOrEqualTo(String value) {
            addCriterion("client_info <=", value, "clientInfo");
            return (Criteria) this;
        }

        public Criteria andClientInfoLike(String value) {
            addCriterion("client_info like", value, "clientInfo");
            return (Criteria) this;
        }

        public Criteria andClientInfoNotLike(String value) {
            addCriterion("client_info not like", value, "clientInfo");
            return (Criteria) this;
        }

        public Criteria andClientInfoIn(List<String> values) {
            addCriterion("client_info in", values, "clientInfo");
            return (Criteria) this;
        }

        public Criteria andClientInfoNotIn(List<String> values) {
            addCriterion("client_info not in", values, "clientInfo");
            return (Criteria) this;
        }

        public Criteria andClientInfoBetween(String value1, String value2) {
            addCriterion("client_info between", value1, value2, "clientInfo");
            return (Criteria) this;
        }

        public Criteria andClientInfoNotBetween(String value1, String value2) {
            addCriterion("client_info not between", value1, value2, "clientInfo");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}