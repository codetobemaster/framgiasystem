package com.framgia.attendance.validator;

import org.apache.poi.hssf.record.formula.functions.T;
import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;

public class HourMinuteValidator implements IValidator<T> {

    private static final long serialVersionUID = 1L;
    
    private IModel<String> timeModel;
    
    public HourMinuteValidator(IModel<String> timeModel) {
        
    }

    @Override
    public void validate(IValidatable<T> validatable) {
    }

}
