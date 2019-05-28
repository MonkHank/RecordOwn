
##### Uri：
 - [x] 统一资源标识符（Uniform Resource Identifier）
 - [x] 用于标识某一互联网资源名称的字符串
```
<scheme>://<authority><absolute path>?<query>#<fragment>
1. 四部分：scheme、authority、path、query
2. 其中authority又包括：host、port
3. 例如：scheme://host:8080/path1/path?query1=123&query2=test
4. Android中的 scheme 默认是 content://

    content://com.example.project:200/folder/subfolder/etc
    \-------/\-------------------/\-/\-------------------/
     scheme          host        port        path
             \----------------------/
               authority  
               
```