/*******************************************************************************
 * Copyright (c) 2015 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/

package org.fusesource.ide.foundation.core.internal;

import org.eclipse.core.runtime.Platform;
import org.jboss.tools.foundation.core.plugin.BaseCorePlugin;
import org.jboss.tools.foundation.core.plugin.log.IPluginLog;
import org.jboss.tools.foundation.core.plugin.log.StatusFactory;
import org.osgi.framework.BundleContext;


/**
 * @author rstryker
 */
public class FoundationCoreActivator extends BaseCorePlugin {
	public static final String PLUGIN_ID = "org.fusesource.ide.foundation.core";

	private static FoundationCoreActivator instance;
	private static BundleContext myContext;

	/**
	 * default constructor
	 */
	public FoundationCoreActivator() {
		instance = this;
	}

	/**
	 * returns the instance
	 * 
	 * @return
	 */
	public static FoundationCoreActivator getDefault() {
		return instance;
	}
	
	public static BundleContext getBundleContext() {
	    return myContext;
	}

    public void start(BundleContext context) throws Exception {
        super.start(context);
        myContext = context;
        registerDebugOptionsListener(PLUGIN_ID, new Trace(this), context);
	}
    
    @Override
    public void stop(BundleContext context) throws Exception {
    	myContext = null;
    	super.stop(context);
    }

	
	/**
	 * Gets message from plugin.properties
	 * @param key
	 * @return
	 */
	public static String getMessage(String key)	{
		return Platform.getResourceString(instance.getBundle(), key);
	}

	/**
	 * Get the IPluginLog for this plugin. This method 
	 * helps to make logging easier, for example:
	 * 
	 *     FoundationCorePlugin.pluginLog().logError(etc)
	 *  
	 * @return IPluginLog object
	 */
	public static IPluginLog pluginLog() {
		return getDefault().pluginLogInternal();
	}

	/**
	 * Get a status factory for this plugin
	 * @return status factory
	 */
	public static StatusFactory statusFactory() {
		return getDefault().statusFactoryInternal();
	}
	
}
