//$HeadURL$
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

 Occam Labs UG (haftungsbeschränkt)
 Godesberger Allee 139, 53175 Bonn
 Germany
 http://www.occamlabs.de/

 e-mail: info@deegree.org
 ----------------------------------------------------------------------------*/

package org.deegree.workspace;

import java.io.InputStream;
import java.net.URL;

/**
 * <code>ResourceProvider</code>
 * 
 * @author <a href="mailto:schmitz@occamlabs.de">Andreas Schmitz</a>
 * @author last edited by: $Author: mschneider $
 * 
 * @version $Revision: 31882 $, $Date: 2011-09-15 02:05:04 +0200 (Thu, 15 Sep 2011) $
 */

public interface ResourceProvider<T extends Resource> {

    /**
     * Returns the namespace for configuration documents that this provider handles.
     * 
     * @return the namespace for configuration documents, never <code>null</code>
     */
    String getConfigNamespace();

    /**
     * Returns the URL for retrieving the configuration document schema.
     * 
     * @return the URL for retrieving the configuration document schema, may be <code>null</code>
     */
    URL getConfigSchema();

    /**
     * Will be called before any call to #create.
     * 
     * @param workspace
     */
    void init( DeegreeWorkspace workspace );

    /**
     * Initialization happens in two phases. The first phase will call this method, the second phase will call #create.
     * Please note that #createMetadata can happen in any order, while {@link ResourceMetadata#create} happens in the
     * proper order defined by the {@link ResourceMetadata}s dependencies.
     * 
     * @param id
     *            the id of the new resource
     * @param configUrl
     *            the url with the configuration
     * @return new resource metadata created from the configuration url, never <code>null</code>
     * @throws ResourceInitException
     */
    ResourceMetadata<T> createMetadata( String id, URL configUrl )
                            throws ResourceInitException;

}