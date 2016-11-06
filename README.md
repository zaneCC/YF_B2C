# Android MVP设计架构优化（灵活、方便迭代）

本架构是通过使用 [RxJava](https://github.com/ReactiveX/RxJava) + [RxAndroid](https://github.com/ReactiveX/RxAndroid) +[Retrofit2](https://github.com/square/retrofit) + [Butterknife](https://github.com/JakeWharton/butterknife)搭建的。


##写作目的
随着项目逐渐壮大，必须有一个完善的框架作为一个支撑，不然难以维护。针对目前流行的框架，如：MVC,MVP,MVVM，我选择了  MVP 作为 Android APP 整体架构。

下图为 MVP 架构图：

![2EBDB2C6-80DD-4C57-9F53-75ED57D6E04D.png](http://upload-images.jianshu.io/upload_images/1677180-272777694553600a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

怕我没说清楚，再具体一点：

![168F2A09-4EC1-43C4-8530-F9D751376580.png](http://upload-images.jianshu.io/upload_images/1677180-eff97e0efa6b1978.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

一开始，我也只是使用简单的 MVP 模式（也就是一个 activity 实现多个 view 接口，同时一个 activity 包含多个 presenter，一个 presenter 包含多个 model，presenter 包含一个 view。其实也就是标准型的 MVP 模式）。但是随着项目逐渐壮大，presenter 里的代码就越来越多，而且业务层代码也越来越不清晰了。由于我的项目架构是使用功能分类的，所以 presenter 也用功能分类，但由于 presenter 只包含一个 view 导致所有需要该 presenter 的 activity 都只能使用同一个 view，这样 view 的耦合度就很高了。对于有代码洁癖的我来说，实在有点难忍。于是我决定优化一下。

在优化之前，必须要知道设计框架目的是什么，设计框架绝对不能为设计而设计！

![装逼图.jpg](http://upload-images.jianshu.io/upload_images/1677180-526c9f5ad85e17a2.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

设计架构的目的是什么:
通过设计使程序模块化，做到模块内部的高聚合和模块之间的低耦合。这样做的好处是使得程序在开发的过程中，开发人员只需要专注于一点，提高程序开发的效率，并且更容易进行后续的测试以及定位问题。但设计不能违背目的，对于不同量级的工程，具体架构的实现方式必然是不同的，切忌犯为了设计而设计，为了架构而架构的毛病。同时希望设计的架构能够模块化，希望把跟业务无关的东西抽象出来，开发人员根本只需要写业务代码就够了。

---
##整体思想
需要解决的问题：
> * 让 presenter 更加灵活，可以灵活使用到多个 activity 中。
> * 让项目业务更加清晰，由于产品是迭代开发的，一般就两周一个版本，必须方便迭代。
> * 新增功能模块必须方便，能形成模版，可以无脑使用。

下面开始介绍优化内容：
     先上一个图，作为一个简单介绍：

![179B9FB5-30F4-4037-A49F-5CE465404620.png](http://upload-images.jianshu.io/upload_images/1677180-99fca48efe34cdb2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

如图所示，view 的展示仍然由 activity 处理，presenter 仍然是中间人，model 还是业务逻辑层。不同的是，Activity 包含的 presenter 有多个，并且是放在 SparseArray 数组里面的，presenter 里也包含多个 view 和多个 model，也都是放在 SparseArray 数组里面的。如何获取具体的 presenter 或者 view、model 呢？ 答案是通过 Action. Action 是重点。Action 放的是具体功能包含的操作。Action 从 Activity 传入到 Presenter，通过 Action 在 Presenter 可以判断用哪个 View 来展示界面。
我不大会画图，可能大家会喷我了。

![文明图.jpg](http://upload-images.jianshu.io/upload_images/1677180-b295f0a869b0fe4c.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

好吧，骂完了，我继续装逼了，举个栗子吧：
> 
比如说登录功能，个人中心需要登录、购物车结算也需要登录，那么登录就可以作为一种功能写一个 Presenter，而个人中心登录需要展示登录成功也需要展示登录失败，而购物车结算也许只需要登录成功就可以了，而不需要展示登录失败，所以我需要有两个不同的 View，一个是用于个人中心的，一个用于购物车结算。Action 怎么设置呢？就需要根据这个登录功能设置两个 Action，因为我们需要在登录 Presenter 里面根据不同的 Action 来选择 View。Presenter 在 Activity 初始化的时候传进去，并且包含展示 View 的接口和 Action，于是 Presenter 就有 Action 了，当 Action 是来自于个人中心时，Presenter 就可以根据个人中心登录的 Action 调用它独有的 View 来展示。当是来自购物车结算页面时，也可以根据 Action 来调用它独有的 View。这样就让 Presenter 灵活了。

不过马上有人会有问题了，这样 Action 会不会很混乱？
是的，如果没有统一管理的话确实会很混乱，这就是设计 Contract 和 BaseAction 的理由。Contract 跟 Presenter 是成双成对的。一个 Presenter 对应了一个 Contract，Contract 里面就把 View、Presenter、Action 绑架到一起了，这样便于管理同时便于修改。

我还害怕我没说清楚，下面给部分代码便于理解：

例如在登录功能中，用户中心登录的操作。整体使用功能分类，如图所示：

![14B84020-ECC2-48F6-8D19-0D9D6A3899E1.png](http://upload-images.jianshu.io/upload_images/1677180-3be0d1b5d751b13e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

user 代表用户中心，（原谅我英语不好，只能写个简单的单词）
可见我这是通过功能分类。首先冲 Activity 来。

在 LoginActivity 初始化的时候，将所需要的 Presenter 都加入进来，并且传入 Action 将他们连接起来。如下图：

![EEFCE22E-2E10-465C-BD25-FE30337B4A9A.png](http://upload-images.jianshu.io/upload_images/1677180-0c6786c214af22c1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
这样业务的 Activity 里面已经木有了 Presenter， addPresenter 是来自基类（BaseActivity）的方法，这样的好处在于我们可以统一管理 Presenter。

用户登录的 Action 可以这样写：

![54F802E8-41B6-4AF0-9F29-FE7332653A14.png](http://upload-images.jianshu.io/upload_images/1677180-f760898b090cd05d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
因为可能有两个 Activity 需要登录功能，而需要展示不同的界面。所以写了两个 Action。

像以前一样 LoginActivity 可以实现多个 View

![885CF2D9-5211-49EB-9708-E3FE7998B06A.png](http://upload-images.jianshu.io/upload_images/1677180-7e55d74bd71cbbed.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
但 View 已经被统一根据具体功能放入了所属的 Contract 里了。

LoginModel 写起来也很简单：

![93DD27F4-A277-4A33-95BA-E331EE239FC1.png](http://upload-images.jianshu.io/upload_images/1677180-53655af8d35b35a7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
Model 跟以前的 MVP 的 Model 层比较没什么差别，主要是考虑到 Model 并不需要抽得太多，Model 也是跟 Presenter 成双成对来写的。

好了，整体思想就是这样的啦。卖个萌：
![卖萌图.JPG](http://upload-images.jianshu.io/upload_images/1677180-b298aa6a1f18c1fe.JPG?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


---
有人可能会想到会不会内存泄漏，比如我这里使用 RxJava，在哪里取消订阅呢？内存在哪里释放掉呢？
答案是：不会发生内存泄漏，内存释放都在 BaseActivity 中处理，因为 Presenter 数组存在在 BaseActivity 中的。而且为了统一处理方便，presenter、view、model 都有一个唯一的基类。
那如何通过 Action 转化成对应的 Presenter、Model、View的？
答案是：通过泛型 ＋ Class<T>，见下图：

![FDA87210-1EF5-4FCF-BF9B-846B9BA92811.png](http://upload-images.jianshu.io/upload_images/1677180-2f4d89b0e6bf1529.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


---

详情请见：

简书：http://www.jianshu.com/p/713977a4a05f
