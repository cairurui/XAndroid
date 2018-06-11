# andfix 学习
通过 activity 的启动流程，顺着找到 PathClassLoader --> BaseClassLoader --> ClassLoader

在 BaseClassLoader 中的 findClass 中找到了一个重要的成员变量 DexPathList,用于存放 Dex 包
之后回去遍历 DexPathList 尝试去 findClass ，找到则返回。

修复的思路就是：将 dex 包插到 DexPathList 的前面，从而先 findClass ，也就找到了没有 hug 的类。













