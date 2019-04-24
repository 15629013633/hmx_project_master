package com.hmx.utils.enums;

public enum IsVerify {
	未使用(0, "未使用"),
	已使用(1, "已使用"),
	;
	
	private Integer state;

    private String info;

    IsVerify(Integer state, String info) {
        this.state = state;
        this.info = info;
    }

    public Integer getState() {
        return state;
    }

    public String getStateInfo() {
        return info;
    }

    public static IsVerify stateOf(int index) {
    	
        for (IsVerify state : IsVerify.values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }
    
    public static IsVerify stateOf(String stateInfo){
    	return valueOf(stateInfo);
    }
    
    public static String getName(int state){
    	for(IsVerify t : IsVerify.values()){
    		if(t.getState() == state){
    			return t.info;
    		}
    	}
    	return null;
    }
}
