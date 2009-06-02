package com.anasoft.os.daofusion.util;

import java.util.ArrayList;
import java.util.List;

import com.anasoft.os.daofusion.criteria.AssociationPath;
import com.anasoft.os.daofusion.criteria.AssociationPathElement;

/**
 * Temporary class, will be removed later.
 */
public class AssociationPathBuilder {

    private List<AssociationPathElement> elements = new ArrayList<AssociationPathElement>();
    
    public AssociationPathBuilder add(String targetPropertyName) {
        elements.add(new AssociationPathElement(targetPropertyName));
        return this;
    }
    
    public AssociationPath build() {
        return new AssociationPath(elements.toArray(new AssociationPathElement[0]));
    }
    
}
