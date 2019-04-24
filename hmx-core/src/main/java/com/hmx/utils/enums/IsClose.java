package com.hmx.utils.enums;

public enum IsClose {
	开放(0, "开放"),
	关闭(1, "关闭"),
	;
	
	private Integer state;

    private String info;

    IsClose(Integer state, String info) {
        this.state = state;
        this.info = info;
    }

    public Integer getState() {
        return state;
    }

    public String getStateInfo() {
        return info;
    }

    public static IsClose stateOf(int index) {
    	
        for (IsClose state : IsClose.values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }
    
    public static IsClose stateOf(String stateInfo){
    	return valueOf(stateInfo);
    }
    
    public static String getName(int state){
    	for(IsClose t : IsClose.values()){
    		if(t.getState() == state){
    			return t.info;
    		}
    	}
    	return null;
    }
}
