#### 蓝牙开发总结
首先，要操作蓝牙，先要在AndroidManifest.xml加入权限
```
<uses-permissionandroid:name="android.permission.BLUETOOTH_ADMIN" />
<uses-permissionandroid:name="android.permission.BLUETOOTH" />
```
Android所有关于蓝牙开发的类都在android.bluetooth包下，只有8个类

1. BluetoothAdapter 蓝牙适配器

直到我们建立bluetoothSocket连接之前，都要不断操作它BluetoothAdapter里的方法很多，常用的有以下几个：
```
cancelDiscovery() 根据字面意思，是取消发现，也就是说当我们正在搜索设备的时候调用这个方法将不再继续搜索
disable()关闭蓝牙
enable()打开蓝牙，这个方法打开蓝牙不会弹出提示，更多的时候我们需要问下用户是否打开，以下这两行代码同样是打开蓝牙，不过会提示用户：

Intent enabler=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
startActivityForResult(enabler,reCode);//同startActivity(enabler);

getAddress()获取本地蓝牙地址
getDefaultAdapter()获取默认BluetoothAdapter，实际上，也只有这一种方法获取BluetoothAdapter
getName()获取本地蓝牙名称
getRemoteDevice(String address)根据蓝牙地址获取远程蓝牙设备
getState()获取本地蓝牙适配器当前状态（感觉可能调试的时候更需要）
isDiscovering()判断当前是否正在查找设备，是返回true
isEnabled()判断蓝牙是否打开，已打开返回true，否则，返回false
listenUsingRfcommWithServiceRecord(String name,UUID uuid)根据名称，UUID创建并返回BluetoothServerSocket，这是创建BluetoothSocket服务器端的第一步
startDiscovery()开始搜索，这是搜索的第一步
 
使用BluetoothAdapter的startDiscovery()方法来搜索蓝牙设备
startDiscovery()方法是一个异步方法，调用后会立即返回。该方法会进行对其他蓝牙设备的搜索，该过程会持续12秒。该方法调用后，搜索过程实际上是在一个System Service中进行的，所以可以调用cancelDiscovery()方法来停止搜索（该方法可以在未执行discovery请求时调用）。
请求Discovery后，系统开始搜索蓝牙设备，在这个过程中，系统会发送以下三个广播：
ACTION_DISCOVERY_START：开始搜索
ACTION_DISCOVERY_FINISHED：搜索结束
ACTION_FOUND：找到设备，这个Intent中包含两个extra fields：EXTRA_DEVICE和EXTRA_CLASS，分别包含BluetooDevice和BluetoothClass。
我们可以自己注册相应的BroadcastReceiver来接收响应的广播，以便实现某些功能
```
2. BluetoothDevice 描述了一个蓝牙设备
```
createRfcommSocketToServiceRecord(UUIDuuid)：根据UUID创建并返回一个BluetoothSocket
getState() ：蓝牙状态这里要说一下，只有在 BluetoothAdapter.STATE_ON 状态下才可以监听，具体可以看andrid api;
这个方法也是我们获取BluetoothDevice的目的――创建BluetoothSocket
这个类其他的方法，如getAddress(),getName(),同BluetoothAdapter
```
3. BluetoothServerSocket

这个类一功只有三个方法两个重载的accept(),accept(inttimeout)两者的区别在于后面的方法指定了过时时间，需要注意的是，执行这两个方法的时候，直到接收到了客户端的请求（或是过期之后），都会阻塞线程，应该放在新线程里运行！
```
void   close()
Closes the object and release any system resources it holds.
void   connect()
Attempt to connect to a remote device.
InputStream getInputStream()
Get the input stream associated with this socket.
OutputStream    getOutputStream()
Get the output stream associated with this socket.
BluetoothDevice getRemoteDevice()
Get the remote device this socket is connecting, or connected, to.
获取远程设备，该套接字连接，或连接到---。
booleanisConnected()
Get the connection status of this socket, ie, whether there is an active connection with remote device.
判断当前的连接状态
```
4. BluetoothSocket

 跟BluetoothServerSocket相对，是客户端一共5个方法，不出意外，都会用到
 ```
 close(),关闭
connect()连接
getInptuStream()获取输入流
getOutputStream()获取输出流
getRemoteDevice()获取远程设备，这里指的是获取bluetoothSocket指定连接的那个远程蓝牙设备
 ```
 5. [BluetoothClass](https://blog.csdn.net/s13383754499/article/details/78436023)
 
- **基本用法**
```
1. 获取本地蓝牙适配器
BluetoothAdapter mAdapter= BluetoothAdapter.getDefaultAdapter();

2.打开蓝牙
if(!mAdapter.isEnabled()){
    //弹出对话框提示用户是后打开
    Intent enabler = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
    startActivityForResult(enabler, REQUEST_ENABLE);
    //不做提示，强行打开，此方法需要权限<uses-permissionandroid:name="android.permission.BLUETOOTH_ADMIN" />
    // mAdapter.enable();
}

3. 搜索设备
//是第一步,可能你会发现没有返回的蓝牙设备
mAdapter.startDiscovery() 

// 第二步，定义BroadcastReceiver,代码如下
// 创建一个接收ACTION_FOUND广播的BroadcastReceiver
private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
     public void onReceive(Context context, Intent intent) {
          String action = intent.getAction();
          // 发现设备
          if (BluetoothDevice.ACTION_FOUND.equals(action)) {
               // 从Intent中获取设备对象
               BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
               // 将设备名称和地址放入array adapter，以便在ListView中显示
               mArrayAdapter.add(device.getName() + "/n" + device.getAddress());
          }else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
               if (mNewDevicesAdapter.getCount() == 0) {
                    Log.v(TAG, "find over");
               }
         }
    };
// 注册BroadcastReceiver
IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
registerReceiver(mReceiver, filter); // 不要忘了之后解除绑定

4. 建立连接
首先Android sdk（2.0以上版本）支持的蓝牙连接是通过BluetoothSocket建立连接，
服务器端（BluetoothServerSocket）和客户端（BluetoothSocket）需指定同样的UUID，
才能建立连接，因为建立连接的方法会阻塞线程，所以服务器端和客户端都应启动新线程连接
(1) 服务端
//UUID格式一般是"xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx"可到
//http://www.uuidgenerator.com 申请
BluetoothServerSocket serverSocket = mAdapter. listenUsingRfcommWithServiceRecord(serverSocketName,UUID);
serverSocket.accept();
(2) 客户端
//通过BroadcastReceiver获取了BLuetoothDevice
BluetoothSocket clienSocket=dcvice. createRfcommSocketToServiceRecord(UUID);
clienSocket.connect();

5. 数据传递
建立BluetoothSocket连接后，数据传递是通过流传递
(1) 获取流
inputStream = socket.getInputStream();
outputStream = socket.getOutputStream();
(2) 写出、读入(JAVA常规操作)
补充一下，使设备能够被搜索
Intent enabler = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
startActivityForResult(enabler,REQUEST_DISCOVERABLE);
```
- **关于蓝牙连接**

可以直接使用下列方式连接
```
final String SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB";
UUID uuid = UUID.fromString(SPP_UUID);
BluetoothSocket socket;
socket = device.createInsecureRfcommSocketToServiceRecord(uuid);
adapter.cancelDiscovery();
socket.connect();
```
1. startDiscovey有可能启动失败

一般程序中会有两步：开启蓝牙、开始寻找设备。顺序执行了开启蓝牙-寻找设备的步骤，但是由于蓝牙还没有完全打开，就开始寻找设备，导致寻找失败。

解决办法
```
adapter = BluetoothAdapter.getDefaultAdapter();
if (adapter == null)      {
// 设备不支持蓝牙
}
// 打开蓝牙
if (!adapter.isEnabled()){
    adapter.enable();
    adapter.cancelDiscovery();
}
// 寻找蓝牙设备，android会将查找到的设备以广播形式发出去
while (!adapter.startDiscovery()){
    Log.e("BlueTooth", "尝试失败");
    try {
        Thread.sleep(100);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}
```
2. 接收数据转换

使用socket.getInputStream接收到的数据是字节流，这样的数据是没法分析的，所以很多情况需要一个byte转十六进制String的函数
```
public static String bytesToHex(byte[] bytes) {
    char[] hexChars = new char[bytes.length * 2];
    for ( int j = 0; j < bytes.length; j++ ) {
        int v = bytes[j] & 0xFF;
        hexChars[j * 2] = hexArray[v >>> 4];
        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
    }
    return new String(hexChars);
}
```
- [ ] [Android Bluetooth 蓝牙开发资料大全](https://blog.csdn.net/djy1992/article/details/10144843)