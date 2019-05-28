#### 网络七层模型(OSI)
- [ ] [链接](https://www.cnblogs.com/wxgblogs/p/5641643.html)
![image](https://images2015.cnblogs.com/blog/927608/201607/927608-20160704203635124-1548160057.jpg)

层级|名称|类型
---|---|---
7	 | 应用层|例如HTTP、SMTP、SNMP、FTP、Telnet、SIP、SSH、NFS、RTSP、XMPP、Whois、ENRP
6	 | 表示层|例如XDR、ASN.1、SMB、AFP、NCP
5|会话层|例如ASAP、TLS、SSH、ISO 8327 / CCITT X.225、RPC、NetBIOS、ASP、Winsock、BSD sockets
4|传输层|例如TCP、UDP、RTP、SCTP、SPX、ATP、IL
3|网络层|例如IP、ICMP、IGMP、IPX、BGP、OSPF、RIP、IGRP、EIGRP、ARP、RARP、 X.25
2|数据链路层|例如以太网、令牌环、HDLC、帧中继、ISDN、ATM、IEEE 802.11、FDDI、PPP
1|物理层|例如线路、无线电、光纤、信鸽

**tcp/ip、http、socket区别**

通过初步的了解，我知道IP协议对应于网络层，TCP协议对应于传输层，而HTTP协议对应于应用层，

　　三者从本质上来说没有可比性，

　　socket则是对TCP/IP协议的封装和应用(程序员层面上)。

　　也可以说，TPC/IP协议是传输层协议，主要解决数据如何在网络中传输，

　　而HTTP是应用层协议，主要解决如何包装数据。

　　关于TCP/IP和HTTP协议的关系，网络有一段比较容易理解的介绍：

　　“我们在传输数据时，可以只使用(传输层)TCP/IP协议，但是那样的话，如果没有应用层，便无法识别数据内容。

　　如果想要使传输的数据有意义，则必须使用到应用层协议。

　　应用层协议有很多，比如HTTP、FTP、TELNET等，也可以自己定义应用层协议。

　　WEB使用HTTP协议作应用层协议，以封装HTTP文本信息，然后使用TCP/IP做传输层协议将它发到网络上。”

　　而我们平时说的最多的socket是什么呢，实际上socket是对TCP/IP协议的封装，Socket本身并不是协议，而是一个调用接口        (API)。

　　通过Socket，我们才能使用TCP/IP协议。

　　实际上，Socket跟TCP/IP协议没有必然的联系。

　　Socket编程接口在设计的时候，就希望也能适应其他的网络协议。

　　所以说，Socket的出现只是使得程序员更方便地使用TCP/IP协议栈而已，是对TCP/IP协议的抽象，