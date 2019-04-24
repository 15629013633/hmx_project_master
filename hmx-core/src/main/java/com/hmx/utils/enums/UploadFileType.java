package com.hmx.utils.enums;

import java.util.List;

import com.hmx.utils.oss.upload.UploadConfig;

public enum UploadFileType {
	图片类型(1, UploadConfig.IMAGETYPE),
	大文件类型(2, UploadConfig.FILETYPE),
	;
	
	private Integer state;

    private List<String> info;

    UploadFileType(Integer state, List<String> info) {
        this.state = state;
        this.info = info;
    }

    public Integer getState() {
        return state;
    }

    public List<String> getStateInfo() {
        return info;
    }

    public static UploadFileType stateOf(int index) {
    	
        for (UploadFileType state : UploadFileType.values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }
    
    public static UploadFileType stateOf(String stateInfo){
    	return valueOf(stateInfo);
    }
    
    public static List<String> getName(int state){
    	for(UploadFileType t : UploadFileType.values()){
    		if(t.getState() == state){
    			return t.info;
    		}
    	}
    	return null;
    }
}
