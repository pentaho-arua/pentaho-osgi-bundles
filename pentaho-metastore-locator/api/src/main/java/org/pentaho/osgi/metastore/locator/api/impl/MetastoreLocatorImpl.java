/*!
 * Copyright 2010 - 2017 Pentaho Corporation.  All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.pentaho.osgi.metastore.locator.api.impl;

import org.pentaho.di.metastore.MetaStoreConst;
import org.pentaho.metastore.api.IMetaStore;
import org.pentaho.metastore.api.exceptions.MetaStoreException;
import org.pentaho.osgi.blueprint.collection.utils.ServiceMap;
import org.pentaho.osgi.metastore.locator.api.MetastoreLocator;
import org.pentaho.osgi.metastore.locator.api.MetastoreProvider;

/**
 * Created by tkafalas on 6/19/2017
 */
public class MetastoreLocatorImpl extends ServiceMap<MetastoreProvider> implements MetastoreLocator {

  @Override
  public IMetaStore getMetastore( String providerKey ) {
    MetastoreProvider provider = super.getItem( providerKey );
    return provider == null ? null : provider.getMetastore();
  }

  @Override
  public IMetaStore getMetastore() {
    IMetaStore metaStore = getMetastore( MetastoreLocator.REPOSITORY_PROVIDER_KEY );
    if ( metaStore == null ) {
      metaStore = getMetastore( MetastoreLocator.LOCAL_PROVIDER_KEY );
    }
    if ( metaStore == null ) {
      try {
        metaStore = MetaStoreConst.openLocalPentahoMetaStore();
      } catch ( MetaStoreException e ) {
        return null;
      }
    }
    return metaStore;
  }

}
