<h1 align="center">
  <br>
    <img src="https://user-images.githubusercontent.com/12044174/85197300-c25ed700-b312-11ea-9a0b-172e7812a75a.png" alt="logo" width="256" height="256">
  <br>
  <br>
  🌈 INTELLIJ RAINBOW FART
  <br>
  <br>
</h1>

<p align="center"> 
<a href="https://github.com/izhangzhihao/intellij-rainbow-fart/releases"><img src="https://img.shields.io/github/release/izhangzhihao/intellij-rainbow-fart.svg?style=flat-square"></a>
<a href="https://plugins.jetbrains.com/plugin/14543-rainbow-fart"><img src="https://img.shields.io/jetbrains/plugin/d/14543-rainbow-fart.svg?style=flat-square"></a>
<a href="https://plugins.jetbrains.com/plugin/14543-rainbow-fart"><img src="https://img.shields.io/jetbrains/plugin/v/14543-rainbow-fart.svg?style=flat-square"></a>
<a href="https://github.com/izhangzhihao/intellij-rainbow-fart/"><img src="https://img.shields.io/badge/contributions-welcome-brightgreen.svg?style=flat-square"></a>
</p>

Languages: 简体中文 | [English](./README.md)

## 下载

https://plugins.jetbrains.com/plugin/14543-rainbow-fart

## 概述

一个在你编程时持续夸你写的牛逼的扩展，可以根据代码关键字播放贴近代码意义的真人语音。Inspired by [vscode-rainbow-fart](https://saekiraku.github.io/vscode-rainbow-fart/)。

## 自定义语音包

<kbd>Settings/Preferences</kbd> > <kbd>Other Settings</kbd> > <kbd>Rainbow Fart</kbd> > <kbd>Custom voice package path</kbd>

如果你想使用 [钉宫语音包](https://github.com/zthxxx/kugimiya-rainbow-fart), 你需要下载最新的 [打包压缩文件](https://github.com/zthxxx/kugimiya-rainbow-fart/releases) 并解压到你想要到目录

例如如下命令：

```
cd /some/dir  # like ~/Downloads
curl -LO https://github.com/zthxxx/kugimiya-rainbow-fart/releases/download/v1.0.2/kugimiya-voice-1.0.2.zip
unzip -q kugimiya-voice-1.0.2.zip -d kugimiya-rainbow-fart
```

然后将 `/some/dir/kugimiya-rainbow-fart` 写入配置中去，点击保存退出，然后重启IDE。

请在[这里](https://github.com/topics/rainbow-fart)查找更多语音包。

## 许可

基于 MIT 开源，包括所有设计资源及音频资源。仓库中的音频资源大部分由真人录音，并且根据 MIT 被授权人义务在此明确：仓库中多媒体资源，来自于[vscode-rainbow-fart](https://saekiraku.github.io/vscode-rainbow-fart/), 作者是@SaekiRaku, 本仓库同样基于[MIT协议](https://github.com/SaekiRaku/vscode-rainbow-fart/blob/master/LICENSE)。
