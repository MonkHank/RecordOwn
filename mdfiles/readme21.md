# 8.WebService

- [ ] [链接](https://blog.csdn.net/wen_haha/article/details/81145456)

- **概述**

WebService是一种基于SOAP协议的远程调用标准，通过webservice可以将不同操作系统平台、不同语言、不同技术整合到一块。在Android SDK中并没有提供调用WebService的库，因此，需要使用第三方的SDK来调用WebService。在Android中适合WebService的SDK有一些，比较常用的有Ksoap2

SOAP协议可以简单地理解为：SOAP=RPC+HTTP+XML，即采用HTTP作为通信协议，RPC（Remote Procedure Call Protocol  远程过程调用协议）作为一致性的调用途径，XML作为数据传送的格式，从而允许服务提供者和服务客户经过防火墙在Internet上进行通信交互

- **调用WebService的方法**
```
1.指定webservice的命名空间和调用网址(.asmx)
2.指定webservice的调用方法名
3.设置调用方法的参数值，如果没有参数，可以省略
    // 设置需调用WebService接口需要传入的参数
    SoapObject rpc = new SoapObject(NAMESPACE, methodName);
    rpc.addProperty("byProvinceName", "");
4.生成webservice方法的soap请求信息。该信息由SoapSerializationEnvelope对象描述
    // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
    envelope.bodyOut = rpc;
    // 设置是否调用的是dotNet开发的WebService
    envelope.dotNet = true;  //true是.net false是java
    envelope.setOutputSoapObject(rpc);
5.创建HttpTransportsSE对象。通过HttpTransportsSE类的构造方法可以指定WebService的URL
    HttpTransportSE transport = new HttpTransportSE(wsdlUrl);
    transport.debug = true;
6.使用call方法调用WebService方法
    // 调用WebService
    transport.call(action, envelope);
7.使用getResponse方法获得WebService方法的返回结果
    // 获取返回的数据
    if (envelope.getResponse()!=null){
        SoapObject   object = (SoapObject) envelope.getResponse();
     }
```
- **注意事项**、

引用接口必须下载一个包 :ksoap2-android-assembly-2.5.2-jar-with-dependencies