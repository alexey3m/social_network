package com.getjavajob.training.web1803.webapp;

import com.getjavajob.training.web1803.common.enums.PhoneType;

import java.beans.PropertyEditorSupport;

public class PhoneTypeEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        switch (text) {
            case "MOBILE":
                this.setValue(PhoneType.MOBILE);
                break;
            case "WORK":
                this.setValue(PhoneType.WORK);
                break;
            case "HOME":
                this.setValue(PhoneType.HOME);
                break;
            default:
                this.setValue("");
                break;
        }
    }
}
