#### 定位方式
定位大致分为三大类：
- GPS定位
- Network定位
    - WIFI定位
    - 基站定位
- AGPS定位

---

- [x] **GPS**：需要GPS硬件支持，直接和卫星交互来获取当前经纬度。
    - <font color="#ff0000">优点：</font>速度快、精度高、可在无网络情况下使用
    - <font color="#ff0000">缺点：</font>首次连接时间长、只能在户外已经开阔地使用，设备上方有遮挡物就不行了、比较耗电。
- [x] **基站定位**：基站定位的方式有多种，一般手机附近的三个基站进行三角定位，由于每个基站的位置是固定的，利用电磁波在这三个基站间中转所需要时间来算出手机所在的坐标，还有一种则是，利用电磁波在这三个基站间中转所需要时间来算出手机所在的坐标；第二种则是利用获取最近的基站的信息，其中包括基站 id，location area code、mobile country code、mobile network code和信号强度，将这些数据发送到google的定位web服务里，就能拿到当前所在的位置信息。
     - <font color="#ff0000">优点：</font>受环境的影响情况较小，不管在室内还是人烟稀少的地方都能用，只要有基站。
     - <font color="#ff0000">缺点：</font>首先需要消耗流量、其实精度没有GPS那么准确，大概在十几米到几十米之间、
- [x] **WIFI定位**：Wifi定位是根据一个固定的WifiMAC地址，通过收集到的该Wifi热点的位置，然后访问网络上的定位服务以获得经纬度坐标。
    - <font color="#ff0000">优点：</font>和基站定位一样，它的优势在于收环境影响较小，只要有Wifi的地方可以使用。
    -  <font color="#ff0000">缺点：</font>需要有wifi、精度不准。
- [x] **AGPS定位**：AssistedGPS（辅助全球卫星定位系统），是结合GSM或GPRS与传统卫星定位，利用基地台代送辅助卫星信息，以缩减GPS芯片获取卫星信号的延迟时间，受遮盖的室内也能借基地台讯号弥补，减轻GPS芯片对卫星的依赖度。和纯GPS、基地台三角定位比较，AGPS能提供范围更广、更省电、速度更快的定位服务，理想误差范围在10公尺以内，日本和美国都已经成熟运用AGPS于LBS服务（Location
 Based Service，基于位置的服务）。AGPS技术是一种结合了网络基站信息和GPS信息对移动台进行定位的技术，可以在GSM/GPRS、WCDMA和CDMA2000网络中使进行用。该技术需要在手机内增加GPS接收机模块，并改造手机的天线，同时要在移动网络上加建位置服务器、差分GPS基准站等设备。AGPS解决方案的优势主要体现在其定位精度上，在室外等空旷地区，其精度在正常的GPS工作环境下，可以达到10米左右，堪称目前定位精度最高的一种定位技术。该技术的另一优点为：首次捕获GPS信号的时间一般仅需几秒，不像GPS的首次捕获时间可能要1分多钟，但很明显，他的硬件要求很高，造价自然高。