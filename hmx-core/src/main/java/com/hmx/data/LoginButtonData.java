package com.hmx.data;

import com.hmx.user.entity.po.Permission;

import java.util.List;

/**
 * @author shi
 * @create 2018-07-04  21:47
 * @description
 **/
public class LoginButtonData {
    private int id;
    private String buttonName;
    private String url;
    private String icon;
    private List<Permission> permissionModels;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getButtonName() {
        return buttonName;
    }

    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<Permission> getPermissionModels() {
        return permissionModels;
    }

    public void setPermissionModels(List<Permission> permissionModels) {
        this.permissionModels = permissionModels;
    }
}
