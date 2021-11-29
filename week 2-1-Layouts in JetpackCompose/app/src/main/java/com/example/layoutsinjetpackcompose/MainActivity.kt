package com.example.layoutsinjetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import com.example.layoutsinjetpackcompose.ui.theme.LayoutsInJetpackComposeTheme


/*
    Creating columns and rows
        Column -> -------
        Row    -> |||||||
        Box    -> ㅁㅁㅁㅁㅁㅁ

        - XML
            => Column, Row 방식의 Rayout 구성 가능

        - Jetpack
            => Column, Row, Box 방식의 Rayout 구성 가능
            => View를 만들거나 갱신할 경우, 전체가 아닌 변경된 부분만 갱신
 */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LayoutsInJetpackComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    // onGloballyPositionedModifier : view사이즈를 가져올 수 있다.
    Text(text = "Hello $name!", modifier = Modifier.onGloballyPositioned {
        android.util.Log.i("TEMP", "size ${it.size}")
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LayoutsInJetpackComposeTheme {
        Greeting("Android")
    }
}

/*
    Slot APIs
        => 사용자 정의 레이아어를 가져오기 위해 Compose가 도입한 패턴
 */

/*
    Mateiral Components
        => Scaffold, TabAppBar 적용

        1. 안드로이드 Compose는 Material Components를 기반
        2. Scaffold : 최상위에 적용 후, modifier.padding을 적용
            => 포함 : TopAppBar, BottomAppBar, FloatingActionButton, Drawer등 제공공
 */

/*
    Working with lists
        1. Compose에서는 LazyColumn을 활용할 수 있다.
        2. ItemTouchHepler.Callback()을 쉽게 구현하기는 어렵다
            => Compose in Layout 참고
 */