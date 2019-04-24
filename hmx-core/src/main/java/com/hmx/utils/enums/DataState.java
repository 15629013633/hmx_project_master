package com.hmx.utils.enums;

public enum DataState {

	正常(0, "正常"),
	删除(1, "删除"),
	;
	
	private Integer state;

    private String info;

    DataState(Integer state, String info) {
        this.state = state;
        this.info = info;
    }

    public Integer getState() {
        return state;
    }

    public String getStateInfo() {
        return info;
    }

    public static DataState stateOf(int index) {
    	
        for (DataState state : DataState.values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }
    
    public static DataState stateOf(String stateInfo){
    	return valueOf(stateInfo);
    }
    
    public static String getName(int state){
    	for(DataState t : DataState.values()){
    		if(t.getState() == state){
    			return t.info;
    		}
    	}
    	return null;
    }
}
