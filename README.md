# Awesome-Android-MVVM
- 什么是MVVM, 为什么需要 MVVM？
- 如何在android中使用DataBinding实现MVVM架构？

## 什么是MVVM , 为什么需要MVVM？

MVVM是Model-View-ViewModel的简写. 它是有三个部分组成：Model、View、ViewModel。

Model：数据模型层。包含业务逻辑和校验逻辑。

View：屏幕上显示的UI界面（layout、views）。

ViewModel：View和Model之间的链接桥梁，处理视图逻辑。

MVVM功能图如下：

![Alt text](https://cdn-images-1.medium.com/max/800/1*VLhXURHL9rGlxNYe9ydqVg.png "MVVM Image")

MVVM架构通过ViewModel隔离了UI层和业务逻辑层，降低程序的耦合度。

### Android App 中MVC的不足
一般来说，我们开发Android App是基于MVC，由于MVC的普及和快速开发的特点，一个app从0开发一般都是基于MVC的。

Activity、Fragment相当于C (Controller), 布局相当于V（View）, 数据层相当于M（Model）

随着业务的增长，Controller里的代码会越来越臃肿，因为它不只要负责业务逻辑，还要控制View的展示。也就是说Activity、Fragment杂糅了Controller和View，耦合变大。并不能算作真正意义上的MVC。

编写代码基本的过程是这样的，在Activity、Fragment中初始化Views，然后拉取数据，成功后把数据填充到View里。

`假如有如下场景`：

> 我们基于MVC开发完第一版本，然后企业需要迭代2.0版本，并且UI界面变化比较大，业务变动较小，怎么办呢？
当2.0的所有东西都已经评审过后。这个时候，新建布局，然后开始按照新的效果图，进行UI布局。然后还要新建Activity、Fragment把相关逻辑和数据填充到新的View上。
如果业务逻辑比较复杂，需要从Activity、Fragment中提取上个版本的所有逻辑，这个时候自己可能就要晕倒了，因为一个复杂的业务，一个Activity几千行代码也是很常见的。千辛万苦做完提取完，可能还会出现很多bug。

一开始我尝试使用MVP架构， MVP功能图如下：

![Alt text](http://rocko-blog.qiniudn.com/Android%E4%B8%AD%E7%9A%84MVP_1.png "MVP Image")



MVP把视图层抽象到View接口，逻辑层抽象到Presenter接口，提到了代码的可读性。降低了视图逻辑和业务逻辑的耦合。

但是有MVP的不足:

1. 接口过多，一定程度影响了编码效率。
2. 业务逻辑抽抽象到Presenter中，较为复杂的界面Activity代码量依然会很多。 
3. 导致Presenter的代码量过大。


这个时候MVVM就闪亮登场了。从上面的MVVM功能图我们知道:

1. 可重用性。你可以把一些视图逻辑放在一个ViewModel里面，让很多view重用这段视图逻辑。
   在Android中，布局里可以进行一个视图逻辑，并且Model发生变化，View也随着发生变化。
2. 低耦合。以前Activity、Fragment中需要把数据填充到View，还要进行一些视图逻辑。现在这些都可在布局中完成（具体代码请看后面）
甚至都不需要再Activity、Fragment去findViewById。这时候Activity、Fragment只需要做好的逻辑处理就可以了。


现在我们回到上面从app1.0到app2.0迭代的问题，如果用MVVM去实现那就比较简单，这个时候不需要动Activity、Fragment，
只需要把布局按照2.0版本的效果实现一遍即可。因为视图逻辑和数据填充已经在布局里了，这就是上面提到的可重用性。

> 发展过程：
MVC->MVP->MVVP


##Android中如何实现MVVM架构？
Google在2015年的已经为我们DataBinding技术。下面就详细讲解如何使用DataBinding。


待续。。。。。。





