# CtkStepPager
ViewPager with stepped TabLayout

| master | dev |
|:-:|:-:|
| [![CircleCI-Master](https://circleci.com/gh/chronotruck/android-ctk-step-pager/tree/master.svg?style=svg&circle-token=dc3aab654f463ab49d4427910c758722461b7531)](https://circleci.com/gh/chronotruck/android-ctk-step-pager/tree/master) | [![CircleCI-Dev](https://circleci.com/gh/chronotruck/android-ctk-step-pager/tree/dev.svg?style=svg&circle-token=dc3aab654f463ab49d4427910c758722461b7531)](https://circleci.com/gh/chronotruck/android-ctk-step-pager/tree/dev) |

<img src="https://raw.githubusercontent.com/chronotruck/android-ctk-step-pager/dev/art/gf.gif"/>



# Download
![minSdkVersion](https://img.shields.io/badge/minSdkVersion-19-blue.svg) [![Release](https://jitpack.io/v/chronotruck/android-ctk-step-pager.svg)](https://jitpack.io/#chronotruck/android-ctk-step-pager)

```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
    implementation 'com.github.chronotruck:android-ctk-step-pager:1.1.0'
}
```



# Setup
Declare the CtkStepPager in your xml
```xml
<com.chronotruck.ctksteppager.CtkStepPager
        android:id="@+id/steppager"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:sp_activeTabBackgroundColor="@color/blue"
        app:sp_activeTabTextColor="@color/white"
        app:sp_doneTabBackgroundColor="@color/green"
        app:sp_doneTabIconColor="@color/green"
        app:sp_inactiveTabBackgroundColor="@color/grey"
        app:sp_inactiveTabTextColor="@color/blue"/>
```

## Custom attributes
### XML
|   Attribute                 |  Description  |    Values     |   Default     |
| --------------------------- | ------------- | ------------- | ------------- |
| sp_activeTabBackgroundColor | active tab background color | color | #277696 (blue) |
| sp_activeTabTextColor | active tab text color  | color  | #FFFFFF (white)  |
| sp_inactiveTabBackgroundColor | inactive tab background color  | color  | #F4F5F5 (grey)  |
| sp_inactiveTabTextColor | inactive tab text color  | color  | #277696 (blue)  |
| sp_doneTabBackgroundColor | done tab background color  | color  | #96BF31 (green)  |
| sp_doneTabIconColor | done tab icon color  | color  | #96BF31 (green)  |

### Kotlin
```kotlin
ctkStepPager.apply {
    activeTabColorBackground = Color.BLUE
    activeTabTextColor = Color.WHITE
    inactiveTabColorBackground = Color.GRAY
    inactiveTabTextColor = Color.BLUE
    doneTabColorBackground = Color.GREEN
    doneTabIconColor = Color.GREEN
}

ctkStepPager.adapter = fragmentPagerAdapter
```
> /!\ set `CtkStepPager.adapter` after customization

## Usage

|   Methods                   |  Description  |
| --------------------------- | ------------- |
| doneCurrentStepTab() | set current step tab **done** |
| doneStepTabUntil(endPositionInclusive: Int) | set steb tabs **done** until `endPositionInclusive`  |
| resetAllStepTabs() | undone all steb tabs |

> `CtkTabLayout` can also be used with your own ViewPager by calling `CtkTabLayout.setupWithViewPager(ViewPager)`



# Author
[Melwin Magalhaes](http://github.com/mcgalanes)

Open source time proudly sponsored by Chronotruck

License
--------


    Copyright 2018 Chronotruck

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
