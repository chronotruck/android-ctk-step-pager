# CtkStepPager
ViewPager with stepped TabLayout

<img src="https://raw.githubusercontent.com/chronotruck/android-ctk-step-pager/dev/art/gf.gif"/>



# Download
```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
    implementation 'com.github.chronotruck:android-ctk-step-pager:1.0.0'
}
```



# Setup
Declare the CtkStepPager in your xml
```html
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
> Call `adapter` after `CtkStepPager` customization

## Usage
`CtkStepPager` is a ready to use view component that contains `CtkTabLayout` and `ViewPager`

|   Methods                   |  Description  |
| --------------------------- | ------------- |
| doneCurrentStepTab() | set current step tab **done** |
| doneStepTabUntil(endPositionInclusive: Int) | set steb tabs **done** until `endPositionInclusive`  |
| resetAllStepTabs() | undone all steb tabs |

> `CtkTabLayout` can also be used with your own ViewPager by calling `CtkTabLayout.setupWithViewPager(ViewPager)`



# Author
[Melwin Magalhaes](http://github.com/mcgalanes)



License
--------


    Copyright 2018 Chronotruck.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.






