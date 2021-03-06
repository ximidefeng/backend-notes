# TCP / IP 概述

Table of Contents
-----------------

* [1. 什么是 TCP / IP?](#1-什么是-tcp--ip)
* [2. TCP / IP 分层模型](#2-tcp--ip-分层模型)
* [3. TCP / IP 是如何工作的?](#3-tcp--ip-是如何工作的)


## 1. 什么是 TCP / IP?

`TCP / IP` 就是互联网协议

好比我们进行篮球比赛，需要有一定的规则（协议），互联网进行通信时，也需要相应的协议



值得注意的是，`TCP / IP` 是泛指协议簇（其中 `TCP` 和 `IP` 这两个协议最重要）



## 2. TCP / IP 分层模型

<div align="center"> <img src="image-20200928165559124.png" width="60%"/> </div><br>



- **物理层**：将上层的比特流（01 二进制流）转换为电压的高低，灯光的闪灭等物理信号，将数据传输出去
- **数据链路层**：通过通信媒介（双绞线电缆，同轴电缆，光纤等）互联的设备之间传输的规范
- **网络层**：基于 `IP` 地址转发分包数据（`IP` 协议在此层）
- **传输层**：让应用程序之间实现通信（`TCP` 协议在此层）
- **应用层**：为应用进程提供服务



## 3. TCP / IP 是如何工作的?

<div align="center"> <img src="image-20200928211040697.png" width="60%"/> </div><br>




下面以邮件收发的例子来简单解释 `TCP / IP` 通信的过程





**第一步：应用程序处理**

新建邮件，将收件人邮箱填好，输入邮件内容：“早上好！”，鼠标点击 ”发送“ 按钮

应用在发送邮件那一刻建立 `TCP` 连接，从而利用这个 `TCP` 连接发送数据。它的过程首先是将应用的数据发送给下一层的 `TCP`，再做实际的转发处理



**第二步：TCP 模块处理**

`TCP` 根据应用的指示，负责建立连接，发送数据，断开连接



为了实现可靠传输的功能，需要在应用层传来的数据附加一个 `TCP` 首部，其中包括：

1. 源端口号和目标端口号（识别发送主机和接收主机上的应用）
2. 序号（判断发送的包哪部分是数据）
3. 校验和（判断数据是否被损坏）



最后将附加了 `TCP` 首部的包再发给 `IP`



**第三步：IP 模块处理**

`IP` 将 `TCP` 传过来的 `TCP` 首部和 `TCP` 数据结合起来当作自己的数据，并附上 `IP` 首部

其中，`IP` 首部包含着接收端 `IP` 地址以及发送端 `IP` 地址



`IP` 包生成后，参考路由控制表决定接受此 `IP` 包的路由 / 主机，将被发送给连接这些路由 / 主机的驱动程序，以实现真正发送数据







**第四步：网络接口处理**

给传过来的 `IP` 包加上以太网首部并进行发送处理，通过物理层传输给接收端

其中，以太网首部包括接收端 `MAC` 地址和发送端 `MAC` 地址





**第五步：网络接口处理（逆向）**

主机收到以太网包之后，首先从以太网的包首部找到 `MAC` 地址判断是否为发给自己的包。如果不是自己的包则丢弃数据

若恰好是发给自己的包，就查找以太网首部中的类型域从而确定以太网协议所传过来的数据类型，将数据传给相应类型的子程序处理（这里是 `IP` 协议）







**第六步：IP 模块处理（逆向）**

`IP` 模块收到 `IP` 包之后，判断包首部的 `IP` 地址是否与自己的 `IP` 地址匹配，并查找上一层协议

若上一层的协议是 `TCP`，则将 `IP` 包首部**之后**的部分传给 `TCP` 处理（若是上一层的协议是 `UDP`，则传给 `UDP` 处理 ） 





**第七步：TCP 模块处理（逆向）**

1. 计算校验和，判断数据是否损坏

2. 按照序号接受数据

3. 检查端口号，确定具体的应用程序



当数据被完整地接受后，会传给由端口号识别的应用程序







**第八步：应用程序处理（逆向）**

邮件保存到本机的硬盘上

“早上好！“







<div align="center"> <img src="image-20200928175308491.png" width="80%"/> </div><br>







<div align="center"> <img src="image-20200928211701192.png" width="80%"/> </div><br>



