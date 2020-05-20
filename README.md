# MQTT
## Add device information
In your project root path, add a `device.properties` file, fill with your device information(configure in aliyun)

```properties
productKey =
deviceName =
deviceSecret =

accessKey =
accessSecret =
```
## Add MQTT to your project
- read instruction [here](https://github.com/WangTingZheng/MQTT/packages/233895)
- read gradle [here](https://help.github.com/articles/configuring-gradle-for-use-with-github-package-registry/)

## Send some information
### Get device information

```java
GetDevice getDevice = new GetDevice("device.properties");
```
### New a access
new a access object to listener objec with aliyun access key and secret.
```java
Access access = new Access(getDevice.getAccessKey(),getDevice.getAccessSecret());
```
### New a Listener

#### New a listener object
```java
Listener listener = new Listener(getDevice.getProductKey(), getDevice.getDeviceName(), getDevice.getDeviceSecret(), new DealServer() {
            @Override
            public String deal_send_back(String msg) {
                if ("hello".equals(msg))
                {
                    return "yes";
                }
                return null;
            }
        });
```

#### start to listen

```java
listener.start();
```

### New a Sender

#### New a Sender object
```java
Sender sender = new Sender();
```
#### Send to certain listener

```java
sender.send(listener, access,"hello", new DealClient() {
            @Override
            public void deal_send_back(String send_back) {
                if ("yes".equals(send_back))
                {
                    System.out.println("done! I got " + send_back);
                }
            }
        });
```
If everything goes normally, your will see `done! I got yes` in System.out console.

**Full code can be viewed [here](https://github.com/WangTingZheng/MQTT/blob/master/src/main/java/com/wangtingzheng/mqtt/App.java)**