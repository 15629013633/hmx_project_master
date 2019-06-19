package com.hmx.files.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2019/4/26.
 */
public class HmxFilesExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Integer offset;

    public HmxFilesExample() {
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

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getOffset() {
        return offset;
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




        public Criteria andFileIdIsNull() {
            addCriterion("file_id is null");
            return (Criteria) this;
        }

        public Criteria andFileIdIsNotNull() {
            addCriterion("file_id is not null");
            return (Criteria) this;
        }

        public Criteria andFileIdEqualTo(Integer value) {
            addCriterion("file_id =", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andCategoryContentIdEqualTo(Integer value) {
            addCriterion("category_content_id =", value, "categoryContentId");
            return (Criteria) this;
        }

        public Criteria andCategoryContentIdIn(List<Integer> values) {
            addCriterion("category_content_id in", values, "categoryContentId");
            return (Criteria) this;
        }

        public Criteria andFileIdNotEqualTo(Integer value) {
            addCriterion("file_id <>", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdGreaterThan(Integer value) {
            addCriterion("file_id >", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("file_id >=", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdLessThan(Integer value) {
            addCriterion("file_id <", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdLessThanOrEqualTo(Integer value) {
            addCriterion("file_id <=", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdLike(Integer value) {
            addCriterion("file_id like", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdNotLike(Integer value) {
            addCriterion("file_id not like", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdIn(List<Integer> values) {
            addCriterion("file_id in", values, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdNotIn(List<Integer> values) {
            addCriterion("file_id not in", values, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdBetween(Integer value1, Integer value2) {
            addCriterion("file_id between", value1, value2, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdNotBetween(Integer value1, Integer value2) {
            addCriterion("file_id not between", value1, value2, "fileId");
            return (Criteria) this;
        }



        public Criteria andFileUrlIsNull() {
            addCriterion("file_url is null");
            return (Criteria) this;
        }

        public Criteria andFileUrlIsNotNull() {
            addCriterion("file_url is not null");
            return (Criteria) this;
        }

        public Criteria andFileUrlEqualTo(String value) {
            addCriterion("file_url =", value, "fileUrl");
            return (Criteria) this;
        }

        public Criteria andFileUrlNotEqualTo(String value) {
            addCriterion("file_url <>", value, "fileUrl");
            return (Criteria) this;
        }

        public Criteria andFileUrlGreaterThan(String value) {
            addCriterion("file_url >", value, "fileUrl");
            return (Criteria) this;
        }

        public Criteria andFileUrlGreaterThanOrEqualTo(String value) {
            addCriterion("file_url >=", value, "fileUrl");
            return (Criteria) this;
        }

        public Criteria andFileUrlLessThan(String value) {
            addCriterion("file_url <", value, "fileUrl");
            return (Criteria) this;
        }

        public Criteria andFileUrlLessThanOrEqualTo(String value) {
            addCriterion("file_url <=", value, "fileUrl");
            return (Criteria) this;
        }

        public Criteria andFileUrlLike(String value) {
            addCriterion("file_url like", value, "fileUrl");
            return (Criteria) this;
        }

        public Criteria andFileUrlNotLike(String value) {
            addCriterion("file_url not like", value, "fileUrl");
            return (Criteria) this;
        }

        public Criteria andFileUrlIn(List<String> values) {
            addCriterion("file_url in", values, "fileUrl");
            return (Criteria) this;
        }

        public Criteria andFileUrlNotIn(List<String> values) {
            addCriterion("file_url not in", values, "fileUrl");
            return (Criteria) this;
        }

        public Criteria andFileUrlBetween(String value1, String value2) {
            addCriterion("file_url between", value1, value2, "fileUrl");
            return (Criteria) this;
        }

        public Criteria andFileUrlNotBetween(String value1, String value2) {
            addCriterion("file_url not between", value1, value2, "fileUrl");
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

        public Criteria andCreateTimeLike(Date value) {
            addCriterion("create_time like", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotLike(Date value) {
            addCriterion("create_time not like", value, "createTime");
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



        public Criteria andNewTimeIsNull() {
            addCriterion("new_time is null");
            return (Criteria) this;
        }

        public Criteria andNewTimeIsNotNull() {
            addCriterion("new_time is not null");
            return (Criteria) this;
        }

        public Criteria andNewTimeEqualTo(Date value) {
            addCriterion("new_time =", value, "newTime");
            return (Criteria) this;
        }

        public Criteria andNewTimeNotEqualTo(Date value) {
            addCriterion("new_time <>", value, "newTime");
            return (Criteria) this;
        }

        public Criteria andNewTimeGreaterThan(Date value) {
            addCriterion("new_time >", value, "newTime");
            return (Criteria) this;
        }

        public Criteria andNewTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("new_time >=", value, "newTime");
            return (Criteria) this;
        }

        public Criteria andNewTimeLessThan(Date value) {
            addCriterion("new_time <", value, "newTime");
            return (Criteria) this;
        }

        public Criteria andNewTimeLessThanOrEqualTo(Date value) {
            addCriterion("new_time <=", value, "newTime");
            return (Criteria) this;
        }

        public Criteria andNewTimeLike(Date value) {
            addCriterion("new_time like", value, "newTime");
            return (Criteria) this;
        }

        public Criteria andNewTimeNotLike(Date value) {
            addCriterion("new_time not like", value, "newTime");
            return (Criteria) this;
        }

        public Criteria andNewTimeIn(List<Date> values) {
            addCriterion("new_time in", values, "newTime");
            return (Criteria) this;
        }

        public Criteria andNewTimeNotIn(List<Date> values) {
            addCriterion("new_time not in", values, "newTime");
            return (Criteria) this;
        }

        public Criteria andNewTimeBetween(Date value1, Date value2) {
            addCriterion("new_time between", value1, value2, "newTime");
            return (Criteria) this;
        }

        public Criteria andNewTimeNotBetween(Date value1, Date value2) {
            addCriterion("new_time not between", value1, value2, "newTime");
            return (Criteria) this;
        }



        public Criteria andStateIsNull() {
            addCriterion("state is null");
            return (Criteria) this;
        }

        public Criteria andStateIsNotNull() {
            addCriterion("state is not null");
            return (Criteria) this;
        }

        public Criteria andStateEqualTo(Integer value) {
            addCriterion("state =", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotEqualTo(Integer value) {
            addCriterion("state <>", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThan(Integer value) {
            addCriterion("state >", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("state >=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThan(Integer value) {
            addCriterion("state <", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThanOrEqualTo(Integer value) {
            addCriterion("state <=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLike(Integer value) {
            addCriterion("state like", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotLike(Integer value) {
            addCriterion("state not like", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateIn(List<Integer> values) {
            addCriterion("state in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotIn(List<Integer> values) {
            addCriterion("state not in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateBetween(Integer value1, Integer value2) {
            addCriterion("state between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotBetween(Integer value1, Integer value2) {
            addCriterion("state not between", value1, value2, "state");
            return (Criteria) this;
        }



        public Criteria andVersionIsNull() {
            addCriterion("version is null");
            return (Criteria) this;
        }

        public Criteria andVersionIsNotNull() {
            addCriterion("version is not null");
            return (Criteria) this;
        }

        public Criteria andVersionEqualTo(Integer value) {
            addCriterion("version =", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotEqualTo(Integer value) {
            addCriterion("version <>", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThan(Integer value) {
            addCriterion("version >", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThanOrEqualTo(Integer value) {
            addCriterion("version >=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThan(Integer value) {
            addCriterion("version <", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThanOrEqualTo(Integer value) {
            addCriterion("version <=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLike(Integer value) {
            addCriterion("version like", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotLike(Integer value) {
            addCriterion("version not like", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionIn(List<Integer> values) {
            addCriterion("version in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotIn(List<Integer> values) {
            addCriterion("version not in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionBetween(Integer value1, Integer value2) {
            addCriterion("version between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotBetween(Integer value1, Integer value2) {
            addCriterion("version not between", value1, value2, "version");
            return (Criteria) this;
        }



        public Criteria andCreateidIsNull() {
            addCriterion("createid is null");
            return (Criteria) this;
        }

        public Criteria andCreateidIsNotNull() {
            addCriterion("createid is not null");
            return (Criteria) this;
        }

        public Criteria andCreateidEqualTo(Integer value) {
            addCriterion("createid =", value, "createid");
            return (Criteria) this;
        }

        public Criteria andCreateidNotEqualTo(Integer value) {
            addCriterion("createid <>", value, "createid");
            return (Criteria) this;
        }

        public Criteria andCreateidGreaterThan(Integer value) {
            addCriterion("createid >", value, "createid");
            return (Criteria) this;
        }

        public Criteria andCreateidGreaterThanOrEqualTo(Integer value) {
            addCriterion("createid >=", value, "createid");
            return (Criteria) this;
        }

        public Criteria andCreateidLessThan(Integer value) {
            addCriterion("createid <", value, "createid");
            return (Criteria) this;
        }

        public Criteria andCreateidLessThanOrEqualTo(Integer value) {
            addCriterion("createid <=", value, "createid");
            return (Criteria) this;
        }

        public Criteria andCreateidLike(Integer value) {
            addCriterion("createid like", value, "createid");
            return (Criteria) this;
        }

        public Criteria andCreateidNotLike(Integer value) {
            addCriterion("createid not like", value, "createid");
            return (Criteria) this;
        }

        public Criteria andCreateidIn(List<Integer> values) {
            addCriterion("createid in", values, "createid");
            return (Criteria) this;
        }

        public Criteria andCreateidNotIn(List<Integer> values) {
            addCriterion("createid not in", values, "createid");
            return (Criteria) this;
        }

        public Criteria andCreateidBetween(Integer value1, Integer value2) {
            addCriterion("createid between", value1, value2, "createid");
            return (Criteria) this;
        }

        public Criteria andCreateidNotBetween(Integer value1, Integer value2) {
            addCriterion("createid not between", value1, value2, "createid");
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
