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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ConfigNodeTest {
	@Test(expected = NullPointerException.class)
	public void testCreateChildNodeWithNullNameThrowsNullPointerException() {
		ConfigNode root = ConfigNode.createRootNode();
		ConfigNode.createChildNode((String) null, "content", root);
	}

	@Test(expected = NullPointerException.class)
	public void testCreateChildNodeWithNullContentThrowsNullPointerException() {
		ConfigNode root = ConfigNode.createRootNode();
		ConfigNode.createChildNode("name", (String) null, root);
	}

	@Test(expected = NullPointerException.class)
	public void testCreateChildNodeWithNullParentThrowsNullPointerException() {
		ConfigNode.createChildNode("name", "content", (ConfigNode) null);
	}

	@Test
	public void testGetNameFromRootNodeIsNull() {
		ConfigNode root = ConfigNode.createRootNode();
		assertNull(root.getName());
	}

	@Test
	public void testGetContentFromRootNodeIsNull() {
		ConfigNode root = ConfigNode.createRootNode();
		assertNull(root.getContent());
	}

	@Test
	public void testGetParentFromRootNodeIsNull() {
		ConfigNode root = ConfigNode.createRootNode();
		assertNull(root.getParent());
	}

	@Test
	public void testIsRootNodeTrue() {
		ConfigNode root = ConfigNode.createRootNode();
		assertTrue(root.isRootNode());
	}

	@Test
	public void testIsRootNodeFalse() {
		ConfigNode root = ConfigNode.createRootNode();
		ConfigNode child = ConfigNode.createChildNode("name", "content", root);

		assertFalse(child.isRootNode());
	}

	@Test
	public void testGetNameFromChildNode() {
		ConfigNode root = ConfigNode.createRootNode();
		ConfigNode child = ConfigNode.createChildNode("name", "content", root);

		assertEquals("name", child.getName());
	}

	@Test
	public void testGetContentFromChildNode() {
		ConfigNode root = ConfigNode.createRootNode();
		ConfigNode child = ConfigNode.createChildNode("name", "content", root);

		assertEquals("content", child.getContent());
	}

	@Test
	public void testGetParentFromChildNode() {
		ConfigNode root = ConfigNode.createRootNode();
		ConfigNode child = ConfigNode.createChildNode("name", "content", root);

		assertEquals(root, child.getParent());
	}

	@Test
	public void testToStringRoot() {
		String expectedString = "ConfigNode {name=null, content=null, childNodeCount=3}";

		ConfigNode root = ConfigNode.createRootNode();
		ConfigNode.createChildNode("child1", "content1", root);
		ConfigNode.createChildNode("child2", "content2", root);
		ConfigNode.createChildNode("child3", "content3", root);

		assertEquals(expectedString, root.toString());
	}

	@Test
	public void testToStringChild() {
		String expectedString = "ConfigNode {name=child1, content=content1, childNodeCount=2}";

		ConfigNode root = ConfigNode.createRootNode();
		ConfigNode child = ConfigNode.createChildNode("child1", "content1", root);
		ConfigNode.createChildNode("child2", "content2", child);
		ConfigNode.createChildNode("child3", "content3", child);

		assertEquals(expectedString, child.toString());
	}
}
