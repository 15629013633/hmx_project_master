package com.hmx.verifylog.entity;

import java.lang.Integer;
import java.lang.String;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class HmxVerifylogExample{
	
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Integer offset;
	
	public HmxVerifylogExample() {
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
        
        
		    
		
        public Criteria andVerifyLogIdIsNull() {
            addCriterion("verify_log_id is null");
            return (Criteria) this;
        }

        public Criteria andVerifyLogIdIsNotNull() {
            addCriterion("verify_log_id is not null");
            return (Criteria) this;
        }

        public Criteria andVerifyLogIdEqualTo(Integer value) {
            addCriterion("verify_log_id =", value, "verifyLogId");
            return (Criteria) this;
        }

        public Criteria andVerifyLogIdNotEqualTo(Integer value) {
            addCriterion("verify_log_id <>", value, "verifyLogId");
            return (Criteria) this;
        }

        public Criteria andVerifyLogIdGreaterThan(Integer value) {
            addCriterion("verify_log_id >", value, "verifyLogId");
            return (Criteria) this;
        }

        public Criteria andVerifyLogIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("verify_log_id >=", value, "verifyLogId");
            return (Criteria) this;
        }

        public Criteria andVerifyLogIdLessThan(Integer value) {
            addCriterion("verify_log_id <", value, "verifyLogId");
            return (Criteria) this;
        }

        public Criteria andVerifyLogIdLessThanOrEqualTo(Integer value) {
            addCriterion("verify_log_id <=", value, "verifyLogId");
            return (Criteria) this;
        }

        public Criteria andVerifyLogIdLike(Integer value) {
            addCriterion("verify_log_id like", value, "verifyLogId");
            return (Criteria) this;
        }

        public Criteria andVerifyLogIdNotLike(Integer value) {
            addCriterion("verify_log_id not like", value, "verifyLogId");
            return (Criteria) this;
        }

        public Criteria andVerifyLogIdIn(List<Integer> values) {
            addCriterion("verify_log_id in", values, "verifyLogId");
            return (Criteria) this;
        }

        public Criteria andVerifyLogIdNotIn(List<Integer> values) {
            addCriterion("verify_log_id not in", values, "verifyLogId");
            return (Criteria) this;
        }

        public Criteria andVerifyLogIdBetween(Integer value1, Integer value2) {
            addCriterion("verify_log_id between", value1, value2, "verifyLogId");
            return (Criteria) this;
        }

        public Criteria andVerifyLogIdNotBetween(Integer value1, Integer value2) {
            addCriterion("verify_log_id not between", value1, value2, "verifyLogId");
            return (Criteria) this;
        }
        
		    
		
        public Criteria andVerifyTypeIsNull() {
            addCriterion("verify_type is null");
            return (Criteria) this;
        }

        public Criteria andVerifyTypeIsNotNull() {
            addCriterion("verify_type is not null");
            return (Criteria) this;
        }

        public Criteria andVerifyTypeEqualTo(Integer value) {
            addCriterion("verify_type =", value, "verifyType");
            return (Criteria) this;
        }

        public Criteria andVerifyTypeNotEqualTo(Integer value) {
            addCriterion("verify_type <>", value, "verifyType");
            return (Criteria) this;
        }

        public Criteria andVerifyTypeGreaterThan(Integer value) {
            addCriterion("verify_type >", value, "verifyType");
            return (Criteria) this;
        }

        public Criteria andVerifyTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("verify_type >=", value, "verifyType");
            return (Criteria) this;
        }

        public Criteria andVerifyTypeLessThan(Integer value) {
            addCriterion("verify_type <", value, "verifyType");
            return (Criteria) this;
        }

        public Criteria andVerifyTypeLessThanOrEqualTo(Integer value) {
            addCriterion("verify_type <=", value, "verifyType");
            return (Criteria) this;
        }

        public Criteria andVerifyTypeLike(Integer value) {
            addCriterion("verify_type like", value, "verifyType");
            return (Criteria) this;
        }

        public Criteria andVerifyTypeNotLike(Integer value) {
            addCriterion("verify_type not like", value, "verifyType");
            return (Criteria) this;
        }

        public Criteria andVerifyTypeIn(List<Integer> values) {
            addCriterion("verify_type in", values, "verifyType");
            return (Criteria) this;
        }

        public Criteria andVerifyTypeNotIn(List<Integer> values) {
            addCriterion("verify_type not in", values, "verifyType");
            return (Criteria) this;
        }

        public Criteria andVerifyTypeBetween(Integer value1, Integer value2) {
            addCriterion("verify_type between", value1, value2, "verifyType");
            return (Criteria) this;
        }

        public Criteria andVerifyTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("verify_type not between", value1, value2, "verifyType");
            return (Criteria) this;
        }
        
		    
		
        public Criteria andVerifyCodeIsNull() {
            addCriterion("verify_code is null");
            return (Criteria) this;
        }

        public Criteria andVerifyCodeIsNotNull() {
            addCriterion("verify_code is not null");
            return (Criteria) this;
        }

        public Criteria andVerifyCodeEqualTo(String value) {
            addCriterion("verify_code =", value, "verifyCode");
            return (Criteria) this;
        }

        public Criteria andVerifyCodeNotEqualTo(String value) {
            addCriterion("verify_code <>", value, "verifyCode");
            return (Criteria) this;
        }

        public Criteria andVerifyCodeGreaterThan(String value) {
            addCriterion("verify_code >", value, "verifyCode");
            return (Criteria) this;
        }

        public Criteria andVerifyCodeGreaterThanOrEqualTo(String value) {
            addCriterion("verify_code >=", value, "verifyCode");
            return (Criteria) this;
        }

        public Criteria andVerifyCodeLessThan(String value) {
            addCriterion("verify_code <", value, "verifyCode");
            return (Criteria) this;
        }

        public Criteria andVerifyCodeLessThanOrEqualTo(String value) {
            addCriterion("verify_code <=", value, "verifyCode");
            return (Criteria) this;
        }

        public Criteria andVerifyCodeLike(String value) {
            addCriterion("verify_code like", value, "verifyCode");
            return (Criteria) this;
        }

        public Criteria andVerifyCodeNotLike(String value) {
            addCriterion("verify_code not like", value, "verifyCode");
            return (Criteria) this;
        }

        public Criteria andVerifyCodeIn(List<String> values) {
            addCriterion("verify_code in", values, "verifyCode");
            return (Criteria) this;
        }

        public Criteria andVerifyCodeNotIn(List<String> values) {
            addCriterion("verify_code not in", values, "verifyCode");
            return (Criteria) this;
        }

        public Criteria andVerifyCodeBetween(String value1, String value2) {
            addCriterion("verify_code between", value1, value2, "verifyCode");
            return (Criteria) this;
        }

        public Criteria andVerifyCodeNotBetween(String value1, String value2) {
            addCriterion("verify_code not between", value1, value2, "verifyCode");
            return (Criteria) this;
        }
        
		    
		
        public Criteria andVerifyObjectIsNull() {
            addCriterion("verify_object is null");
            return (Criteria) this;
        }

        public Criteria andVerifyObjectIsNotNull() {
            addCriterion("verify_object is not null");
            return (Criteria) this;
        }

        public Criteria andVerifyObjectEqualTo(String value) {
            addCriterion("verify_object =", value, "verifyObject");
            return (Criteria) this;
        }

        public Criteria andVerifyObjectNotEqualTo(String value) {
            addCriterion("verify_object <>", value, "verifyObject");
            return (Criteria) this;
        }

        public Criteria andVerifyObjectGreaterThan(String value) {
            addCriterion("verify_object >", value, "verifyObject");
            return (Criteria) this;
        }

        public Criteria andVerifyObjectGreaterThanOrEqualTo(String value) {
            addCriterion("verify_object >=", value, "verifyObject");
            return (Criteria) this;
        }

        public Criteria andVerifyObjectLessThan(String value) {
            addCriterion("verify_object <", value, "verifyObject");
            return (Criteria) this;
        }

        public Criteria andVerifyObjectLessThanOrEqualTo(String value) {
            addCriterion("verify_object <=", value, "verifyObject");
            return (Criteria) this;
        }

        public Criteria andVerifyObjectLike(String value) {
            addCriterion("verify_object like", value, "verifyObject");
            return (Criteria) this;
        }

        public Criteria andVerifyObjectNotLike(String value) {
            addCriterion("verify_object not like", value, "verifyObject");
            return (Criteria) this;
        }

        public Criteria andVerifyObjectIn(List<String> values) {
            addCriterion("verify_object in", values, "verifyObject");
            return (Criteria) this;
        }

        public Criteria andVerifyObjectNotIn(List<String> values) {
            addCriterion("verify_object not in", values, "verifyObject");
            return (Criteria) this;
        }

        public Criteria andVerifyObjectBetween(String value1, String value2) {
            addCriterion("verify_object between", value1, value2, "verifyObject");
            return (Criteria) this;
        }

        public Criteria andVerifyObjectNotBetween(String value1, String value2) {
            addCriterion("verify_object not between", value1, value2, "verifyObject");
            return (Criteria) this;
        }
        
		    
		
        public Criteria andAddTimeIsNull() {
            addCriterion("add_time is null");
            return (Criteria) this;
        }

        public Criteria andAddTimeIsNotNull() {
            addCriterion("add_time is not null");
            return (Criteria) this;
        }

        public Criteria andAddTimeEqualTo(Date value) {
            addCriterion("add_time =", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotEqualTo(Date value) {
            addCriterion("add_time <>", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeGreaterThan(Date value) {
            addCriterion("add_time >", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("add_time >=", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeLessThan(Date value) {
            addCriterion("add_time <", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeLessThanOrEqualTo(Date value) {
            addCriterion("add_time <=", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeLike(Date value) {
            addCriterion("add_time like", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotLike(Date value) {
            addCriterion("add_time not like", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeIn(List<Date> values) {
            addCriterion("add_time in", values, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotIn(List<Date> values) {
            addCriterion("add_time not in", values, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeBetween(Date value1, Date value2) {
            addCriterion("add_time between", value1, value2, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotBetween(Date value1, Date value2) {
            addCriterion("add_time not between", value1, value2, "addTime");
            return (Criteria) this;
        }
        
		    
		
        public Criteria andIsVerifyIsNull() {
            addCriterion("is_verify is null");
            return (Criteria) this;
        }

        public Criteria andIsVerifyIsNotNull() {
            addCriterion("is_verify is not null");
            return (Criteria) this;
        }

        public Criteria andIsVerifyEqualTo(Integer value) {
            addCriterion("is_verify =", value, "isVerify");
            return (Criteria) this;
        }

        public Criteria andIsVerifyNotEqualTo(Integer value) {
            addCriterion("is_verify <>", value, "isVerify");
            return (Criteria) this;
        }

        public Criteria andIsVerifyGreaterThan(Integer value) {
            addCriterion("is_verify >", value, "isVerify");
            return (Criteria) this;
        }

        public Criteria andIsVerifyGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_verify >=", value, "isVerify");
            return (Criteria) this;
        }

        public Criteria andIsVerifyLessThan(Integer value) {
            addCriterion("is_verify <", value, "isVerify");
            return (Criteria) this;
        }

        public Criteria andIsVerifyLessThanOrEqualTo(Integer value) {
            addCriterion("is_verify <=", value, "isVerify");
            return (Criteria) this;
        }

        public Criteria andIsVerifyLike(Integer value) {
            addCriterion("is_verify like", value, "isVerify");
            return (Criteria) this;
        }

        public Criteria andIsVerifyNotLike(Integer value) {
            addCriterion("is_verify not like", value, "isVerify");
            return (Criteria) this;
        }

        public Criteria andIsVerifyIn(List<Integer> values) {
            addCriterion("is_verify in", values, "isVerify");
            return (Criteria) this;
        }

        public Criteria andIsVerifyNotIn(List<Integer> values) {
            addCriterion("is_verify not in", values, "isVerify");
            return (Criteria) this;
        }

        public Criteria andIsVerifyBetween(Integer value1, Integer value2) {
            addCriterion("is_verify between", value1, value2, "isVerify");
            return (Criteria) this;
        }

        public Criteria andIsVerifyNotBetween(Integer value1, Integer value2) {
            addCriterion("is_verify not between", value1, value2, "isVerify");
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

