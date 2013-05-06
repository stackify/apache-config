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

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Test;

public class ApacheConfigParserTest {
	@Test(expected = NullPointerException.class)
	public void testParseWithNullInputStreamThrowsNullPointerException() throws IOException {
		ApacheConfigParser parser = new ApacheConfigParser();
		parser.parse((InputStream) null);
	}

	@Test
	public void testParseExampleConfig() throws IOException {
		InputStream inputStream = getClass().getResourceAsStream("/www.example.conf");

		ApacheConfigParser parser = new ApacheConfigParser();
		ConfigNode root = parser.parse(inputStream);

		verifyNode(null, null, 2, root);

		List<ConfigNode> vhosts = root.getChildren();
		assertEquals(2, vhosts.size());

		// Virtual Host 1
		{
			ConfigNode vhost = vhosts.get(0);
			verifyNode("VirtualHost", "*", 2, vhost);

			List<ConfigNode> directives = vhost.getChildren();

			ConfigNode serverName = directives.get(0);
			verifyNode("ServerName", "example.com", 0, serverName);

			ConfigNode redirect = directives.get(1);
			verifyNode("Redirect", "permanent / http://www.example.com/", 0, redirect);
		}

		// Virtual Host 2
		{
			ConfigNode vhost = vhosts.get(1);
			verifyNode("VirtualHost", "*", 7, vhost);

			List<ConfigNode> vhostDirectives = vhost.getChildren();

			ConfigNode documentRoot = vhostDirectives.get(0);
			verifyNode("DocumentRoot", "\"/usr/local/apache/htdocs/www.example.com\"", 0, documentRoot);

			ConfigNode serverName = vhostDirectives.get(1);
			verifyNode("ServerName", "www.example.com", 0, serverName);

			ConfigNode rewriteEngine = vhostDirectives.get(2);
			verifyNode("RewriteEngine", "On", 0, rewriteEngine);

			ConfigNode rewriteRule = vhostDirectives.get(3);
			verifyNode("RewriteRule",
					"^/$ /w/extract2.php?title=Www.example.com_portal&template=Www.example.com_template [L]", 0,
					rewriteRule);

			ConfigNode vhostTwoPhpAdminFlag = vhostDirectives.get(4);
			verifyNode("php_admin_flag", "engine on", 0, vhostTwoPhpAdminFlag);

			// Directory 1
			{
				ConfigNode directory = vhostDirectives.get(5);
				verifyNode("Directory", "\"/usr/local/apache/htdocs/www\"", 3, directory);

				List<ConfigNode> directoryDirectives = directory.getChildren();

				ConfigNode order = directoryDirectives.get(0);
				verifyNode("Order", "Deny,Allow", 0, order);

				ConfigNode allow = directoryDirectives.get(1);
				verifyNode("Allow", "from env=tarpitted_bots", 0, allow);

				ConfigNode deny = directoryDirectives.get(2);
				verifyNode("Deny", "from env=bad_bots", 0, deny);
			}

			// Directory 2
			{
				ConfigNode directory = vhostDirectives.get(6);
				verifyNode("Directory", "\"/usr/local/apache/htdocs/www/stats\"", 8, directory);

				List<ConfigNode> directoryDirectives = directory.getChildren();

				ConfigNode allowOverride = directoryDirectives.get(0);
				verifyNode("AllowOverride", "All", 0, allowOverride);

				ConfigNode expiresByTypeGif = directoryDirectives.get(1);
				verifyNode("ExpiresByType", "image/gif A0", 0, expiresByTypeGif);

				ConfigNode expiresByTypePng = directoryDirectives.get(2);
				verifyNode("ExpiresByType", "image/png A0", 0, expiresByTypePng);

				ConfigNode expiresByTypeJpeg = directoryDirectives.get(3);
				verifyNode("ExpiresByType", "image/jpeg A0", 0, expiresByTypeJpeg);

				ConfigNode expiresByTypeCss = directoryDirectives.get(4);
				verifyNode("ExpiresByType", "text/css A2592000", 0, expiresByTypeCss);

				ConfigNode expiresByTypeTextJavascript = directoryDirectives.get(5);
				verifyNode("ExpiresByType", "text/javascript A2592000", 0, expiresByTypeTextJavascript);

				ConfigNode expiresByTypeAppJavascript = directoryDirectives.get(6);
				verifyNode("ExpiresByType", "application/x-javascript A2592000", 0, expiresByTypeAppJavascript);

				ConfigNode expiresByTypeHtml = directoryDirectives.get(7);
				verifyNode("ExpiresByType", "text/html A0", 0, expiresByTypeHtml);
			}
		}

	}

	private static void verifyNode(String expectedName, String expectedContent, int expectedChildCount, ConfigNode node) {
		assertEquals(expectedName, node.getName());
		assertEquals(expectedContent, node.getContent());
		assertEquals(expectedChildCount, node.getChildren().size());
	}
}
