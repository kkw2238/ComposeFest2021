package com.example.basicscoadlab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.basicscoadlab.ui.theme.BasicsCoadlabTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*
          SetContent안에 XML 대신 레이아웃을 구성할 Composable함수를 들어갈 수 있다.

             => 현재는 XML이 아닌 Composable함수를 넣는 방식으로 UI를 구성
        */
        setContent {
            BasicsCoadlabTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("Android")
                }
            }
        }
    }
}


// @Composable 어노테이션을 추가하여 Composable Function으로 활용 가능
@Composable
fun Greeting(name: String) {
    Surface(color = MaterialTheme.colors.onSurface) {
        Text(text = "Hello $name!", modifier = Modifier.padding(24.dp))
    }
}

// @Preview 어노테이션을 추가하면 UI구성과 같은 것을 Preview할 수 있다.
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BasicsCoadlabTheme {
        Greeting("Android")
    }
}

/*
    Reusing Composables

        - @Composable 어노테이션을 추가하여 함수를 분리하거나 독립적으로 편집이 가능 !
            => XML에서 <include>를 사용했던 것 처럼
 */

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

/*
    State in compose
        - 초기 Composition
            1. 데이터의 변경
            2. ReComposition
                => 데이터가 변경될 때, Composition을 업데이트 하기 위해 Composable을 다시 진행 )

        - Remember 컴포지블
            1. 데이터가 변경될 때, (State<T>가 변경될 때) Remember 컴포지블로 객체 저장
            2. 수정할 때 mutableStateOf<T>로 접근
 */

/*
    State hoisting
        - Callback으로 Compose를 구성하는 법
            1. View visible / invisible
 */

/*
    Creating a performant lazy list
        => 아무리 Compose를 사용하여 View를 구성하는 비용이 적어도,
           View가 너무 많을 경우 비용이 너무 많이든다.

           화면에 보이는 것만 지연으로 Composable을 구성함으로서 비용 절감
                -> 미리 만들어 놓고 재활용 하는 것보다
                   Jetpack Compose에서 View를 구성하는 것이 비용이 더 적기에 이런 방식을 사용

        - Lazy Column
        - Lazy Row
        - RecyclerView
*/

/*
    Persisting state
        - RememberSaveable
            => 화면을 전환하거나 테마를 바꾸게 될 경우,
               Activity가 재구성 되면서 기존 Remeber로 저장했던 부분이 날아갈 수 있기에,
               이 부분을 저장 해놓기 위해서 RememberSaveable를 사용하여 저장
 */