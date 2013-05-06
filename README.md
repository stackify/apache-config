apache-config
=============

A simple config parser for Apache HTTP Server config files.

Usage
-----
The parser builds a tree of apache configuration.  So, for example, if you wanted to find all of the virtual hosts you could do the following.

```java
InputStream inputStream = new FileInputStream(new File("/etc/httpd/conf/httpd.conf"));

ApacheConfigParser parser = new ApacheConfigParser();
ConfigNode config = parser.parse(inputStream);

for (ConfigNode child : config.getChildren()) {
	if (child.getName().equals("VirtualHost")) {
    	System.out.println(child.getContent());
    }
}
```

License
=======

    Copyright 2013 Stackify, LLC.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.