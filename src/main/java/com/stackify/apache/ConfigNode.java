/*
 * Copyright 2013 Stackify
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */
package com.stackify.apache;

import java.util.ArrayList;
import java.util.List;

/**
 * A node in an Apache httpd configuration tree.
 * 
 * @author John Leacox
 * 
 */
public class ConfigNode {
	private final String name;
	private final String content;
	private final List<ConfigNode> children = new ArrayList<ConfigNode>();

	private final ConfigNode parent;

	/**
	 * Private constructor. {@code ConfigNode} instances should be created via the creation factory methods.
	 * 
	 * @param name
	 *            the node name
	 * @param content
	 *            the node content
	 * @param parent
	 *            the parent of the node
	 */
	private ConfigNode(String name, String content, ConfigNode parent) {
		this.name = name;
		this.content = content;
		this.parent = parent;
	}

	/**
	 * Creates a root node.
	 * 
	 * <p>
	 * A root node will have a null parent, name, and content. It is the top level of the configuration tree with child
	 * nodes containing actual values.
	 * 
	 * @return a new root configuration node
	 */
	public static ConfigNode createRootNode() {
		return new ConfigNode(null, null, null);
	}

	/**
	 * Creates a child node
	 * 
	 * <p>
	 * A child node contains a configuration name and configuration content as well as a parent node in the tree. If the
	 * child node is an apache configuration section it may also have child nodes of its own.
	 * 
	 * @param name
	 *            the configuration name (cannot be null)
	 * @param content
	 *            the configuration content (cannot be null)
	 * @param parent
	 *            the child nodes parent (cannot be null)
	 * @return a new child configuration node
	 * @throws NullPointerException
	 *             if name, content, or parent is null
	 */
	public static ConfigNode createChildNode(String name, String content, ConfigNode parent) {
		if (name == null) {
			throw new NullPointerException("name: null");
		}
		if (content == null) {
			throw new NullPointerException("content: null");
		}
		if (parent == null) {
			throw new NullPointerException("parent: null");
		}

		ConfigNode child = new ConfigNode(name, content, parent);
		parent.addChild(child);

		return child;
	}

	/**
	 * 
	 * @return the configuration name; null if this is a root node
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @return the configuration content; null if this is a root node
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 
	 * @return The nodes parent; null if this is a root node
	 */
	public ConfigNode getParent() {
		return parent;
	}

	/**
	 * 
	 * @return a list of child configuration nodes
	 */
	public List<ConfigNode> getChildren() {
		return children;
	}

	/**
	 * 
	 * @return true if this is a root node; false otherwise
	 */
	public boolean isRootNode() {
		return parent == null;
	}

	@Override
	public String toString() {
		return "ConfigNode {name=" + name + ", content=" + content + ", childNodeCount=" + children.size() + "}";
	}

	private void addChild(ConfigNode child) {
		children.add(child);
	}
}
