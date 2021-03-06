/*******************************************************************************
 * Copyright (c) 2013 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/

package org.fusesource.ide.jmx.activemq.navigator;

import java.util.Collection;

import org.eclipse.swt.graphics.Image;
import org.fusesource.ide.foundation.ui.tree.RefreshableCollectionNode;
import org.fusesource.ide.jmx.activemq.ActiveMQJMXPlugin;
import org.fusesource.ide.jmx.activemq.internal.BrokerFacade;
import org.fusesource.ide.jmx.activemq.internal.ProducerViewFacade;
import org.jboss.tools.jmx.ui.ImageProvider;


public class TopicProducersNode extends RefreshableCollectionNode implements ImageProvider {

	private final BrokerNode brokerNode;
	private final BrokerFacade facade;
	private final TopicNode topicNode;

	public TopicProducersNode(TopicNode topicNode) {
		super(topicNode);
		this.topicNode = topicNode;
		this.brokerNode = topicNode.getBrokerNode();
		this.facade = topicNode.getFacade();
	}

	@Override
	public String toString() {
		return "Producers";
	}

	@Override
	protected void loadChildren() {
		try {
			Collection<ProducerViewFacade> list = getFacade().getTopicProducers(topicNode.getName());
			if (list != null) {
				for (ProducerViewFacade mbean : list) {
					addChild(new ProducerNode(this, topicNode, mbean));
				}
			}
		} catch (Exception e) {
			brokerNode.handleException(this, e);
		}
	}

	@Override
	public void refresh() {
		// TODO for some reason this doesn't work, so lets refresh the broker
		getBrokerNode().refresh();
	}

	public BrokerNode getBrokerNode() {
		return brokerNode;
	}

	@Override
	public Image getImage() {
		return ActiveMQJMXPlugin.getDefault().getImage("topic_folder.png");
	}

	protected BrokerFacade getFacade() {
		return getBrokerNode().getFacade();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return obj instanceof TopicProducersNode && obj.hashCode() == hashCode();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		if(isConnectionAvailable()) {
			return ("AMQTopicProducersNode-" + brokerNode.toString() + "-" + topicNode.getName() + "-" + toString() + "-" + getConnection().getProvider().getName(getConnection())).hashCode();
		}
		return super.hashCode();
	}
}
