//$HeadURL: svn+ssh://lbuesching@svn.wald.intevation.de/deegree/base/trunk/resources/eclipse/files_template.xml $
/*----------------------------------------------------------------------------
 This file is part of deegree, http://deegree.org/
 Copyright (C) 2001-2010 by:
 - Department of Geography, University of Bonn -
 and
 - lat/lon GmbH -

 This library is free software; you can redistribute it and/or modify it under
 the terms of the GNU Lesser General Public License as published by the Free
 Software Foundation; either version 2.1 of the License, or (at your option)
 any later version.
 This library is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 details.
 You should have received a copy of the GNU Lesser General Public License
 along with this library; if not, write to the Free Software Foundation, Inc.,
 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

 Contact information:

 lat/lon GmbH
 Aennchenstr. 19, 53177 Bonn
 Germany
 http://lat-lon.de/

 Department of Geography, University of Bonn
 Prof. Dr. Klaus Greve
 Postfach 1147, 53001 Bonn
 Germany
 http://www.geographie.uni-bonn.de/deegree/

 e-mail: info@deegree.org
 ----------------------------------------------------------------------------*/
package org.deegree.client.mdeditor.model;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO add class documentation here
 * 
 * @author <a href="mailto:buesching@lat-lon.de">Lyn Buesching</a>
 * @author last edited by: $Author: lyn $
 * 
 * @version $Revision: $, $Date: $
 */
public class FormGroup implements FormElement {

    private String id;

    private String label;

    private String title;

    // private boolean referenced = false;

    private List<FormElement> formElements = new ArrayList<FormElement>();

    private int occurence = 1;

    public FormGroup( String id, String label, String title, int occurence ) {
        this.id = id;
        this.label = label;
        this.title = title;
        this.occurence = occurence;
    }

    public void setFormElements( List<FormElement> formElements ) {
        this.formElements = formElements;
    }

    public List<FormElement> getFormElements() {
        return formElements;
    }

    public void setId( String id ) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setTitle( String title ) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setLabel( String label ) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setOccurence( int occurence ) {
        this.occurence = occurence;
    }

    public int getOccurence() {
        return occurence;
    }

    public void addFormElement( FormElement parseFormGroup ) {
        if ( formElements == null ) {
            formElements = new ArrayList<FormElement>();
        }
        formElements.add( parseFormGroup );
    }

    // public void setReferenced( boolean referenced ) {
    // this.referenced = referenced;
    // }
    //
    // public boolean isReferenced() {
    // return referenced;
    // }

    public void reset() {
        for ( FormElement fe : formElements ) {
            fe.reset();
        }
    }

    @Override
    public String toString() {
        return id + " (" + getFormElements() + ")";
    }
}
