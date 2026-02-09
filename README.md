# 🌻 植物大战僵尸 Android 版 🧟

一个使用 Kotlin 开发的《植物大战僵尸》风格塔防游戏，完全基于 Android 原生 Canvas 实现。

## ✨ 特性

- 🎮 经典塔防玩法
- 🌱 5种不同的植物
- 🧟 3种僵尸类型
- 📊 10波递增难度的关卡
- ☀️ 阳光资源系统
- 🎯 碰撞检测和战斗系统
- 📱 完整的触摸操作支持
- 🎨 简洁的2D图形界面

## 🎬 游戏截图

游戏界面包含：
- 顶部：植物卡片选择区
- 中部：5×9 草坪战场网格
- 右侧：阳光和波次显示

## 🎯 游戏内容

### 植物系统
| 植物 | 阳光消耗 | 冷却 | 血量 | 特性 |
|------|---------|------|------|------|
| 🌻 向日葵 | 50 | 7.5秒 | 100 | 每25秒生产25阳光 |
| 🌿 豌豆射手 | 100 | 7.5秒 | 100 | 发射豌豆攻击僵尸 |
| 🥜 坚果墙 | 50 | 30秒 | 400 | 高血量防御单位 |
| ❄️ 寒冰射手 | 175 | 7.5秒 | 100 | 发射冰豌豆减速僵尸 |
| 🍒 樱桃炸弹 | 150 | 50秒 | 100 | 1.5秒后范围爆炸 |

### 僵尸类型
| 僵尸 | 血量 | 速度 | 特点 |
|------|------|------|------|
| 🧟 普通僵尸 | 200 | 0.5 | 基础僵尸 |
| 🚧 路障僵尸 | 370 | 0.5 | 戴着路障头盔 |
| 🪣 铁桶僵尸 | 650 | 0.5 | 戴着铁桶，超高防御 |

### 关卡波次
- 第1-2波：基础教学，只有普通僵尸
- 第3-5波：开始出现路障僵尸
- 第6-9波：混合出现各种僵尸
- 第10波：终极大波！20个僵尸同时进攻

## 🚀 快速开始

### 环境要求
- Android Studio Arctic Fox 或更高版本
- JDK 8 或更高
- Android SDK API 24+ (Android 7.0+)
- 至少 8GB RAM

### 安装步骤

1. **克隆或下载项目**
```bash
# 如果有 git 仓库
git clone https://github.com/yourusername/plants-vs-zombies-android.git
```

2. **在 Android Studio 中打开**
```
File → Open → 选择项目文件夹
```

3. **同步 Gradle**
```
等待 Gradle 自动同步，或点击 "Sync Project with Gradle Files"
```

4. **运行项目**
```
点击 Run 按钮 (绿色三角形)
选择设备或模拟器
等待安装完成
```

详细步骤请查看 [QUICK_START.md](QUICK_START.md)

## 📖 文档

- [📘 项目结构说明](PROJECT_STRUCTURE.md) - 了解项目架构
- [🚀 快速开始指南](QUICK_START.md) - 5分钟上手指南
- [📚 完整开发指南](DEVELOPMENT_GUIDE.md) - 深入理解和扩展

## 🎮 游戏操作

### 基础操作
1. **选择植物**：点击顶部的植物卡片
2. **放置植物**：选中植物后点击草坪网格
3. **收集阳光**：点击下落的阳光或向日葵生产的阳光
4. **取消选择**：再次点击已选中的卡片

### 游戏策略
- 优先放置向日葵积累阳光
- 在僵尸来临前建立防线
- 合理搭配攻击和防御植物
- 注意植物冷却时间
- 最后一波要留足资源

## 🏗️ 项目结构

```
PlantsVsZombies/
├── MainActivity.kt          # 应用入口
├── GameView.kt             # 游戏视图层
├── GameEngine.kt           # 游戏引擎核心
├── models/                 # 数据模型
│   ├── GameObject.kt
│   ├── Plant.kt
│   ├── Zombie.kt
│   ├── Projectile.kt
│   └── Sun.kt
├── plants/                 # 植物实现
│   ├── Sunflower.kt
│   ├── Peashooter.kt
│   ├── WallNut.kt
│   ├── SnowPea.kt
│   └── CherryBomb.kt
├── zombies/               # 僵尸实现
│   ├── NormalZombie.kt
│   ├── ConeZombie.kt
│   └── BucketZombie.kt
├── managers/              # 管理器
│   ├── ResourceManager.kt
│   ├── CollisionManager.kt
│   └── WaveManager.kt
└── ui/                    # UI组件
    └── PlantCard.kt
```

## 🔧 技术栈

- **开发语言**：Kotlin 1.9.20
- **最低系统**：Android 7.0 (API 24)
- **目标系统**：Android 14 (API 34)
- **渲染引擎**：Android Canvas 2D
- **架构模式**：MVC
- **构建工具**：Gradle 8.2

## 🎨 核心技术

- **游戏循环**：60 FPS 刷新率
- **碰撞检测**：矩形边界检测
- **对象管理**：CopyOnWriteArrayList 线程安全
- **触摸处理**：Android MotionEvent
- **资源管理**：单例模式管理器

## 🚧 未来计划

- [ ] 添加音效和背景音乐
- [ ] 实现更多植物类型
- [ ] 添加更多僵尸类型
- [ ] 关卡选择系统
- [ ] 成就系统
- [ ] 排行榜功能
- [ ] 使用真实图片资源替代简单图形
- [ ] 添加粒子效果
- [ ] 实现存档功能
- [ ] 多语言支持

## 🤝 贡献指南

欢迎贡献代码！请遵循以下步骤：

1. Fork 本项目
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 开启 Pull Request

## 📝 代码规范

- 使用 Kotlin 官方代码风格
- 类名使用 PascalCase
- 函数和变量使用 camelCase
- 常量使用 UPPER_SNAKE_CASE
- 添加必要的注释

## 🐛 问题反馈

如果遇到问题：
1. 查看 [QUICK_START.md](QUICK_START.md) 常见问题
2. 查看 [DEVELOPMENT_GUIDE.md](DEVELOPMENT_GUIDE.md) 调试技巧
3. 提交 Issue 并描述问题详情

## 📄 许可证

本项目仅供学习和研究使用。

## 🙏 致谢

- 灵感来源于 PopCap Games 的经典游戏《植物大战僵尸》
- 感谢 Android 开发社区的支持

## 📞 联系方式

- 项目维护者：[Your Name]
- Email: your.email@example.com
- 项目主页：https://github.com/yourusername/plants-vs-zombies-android

---

⭐ 如果这个项目对你有帮助，请给一个 Star！

🌻 享受游戏，保卫你的草坪！🧟
