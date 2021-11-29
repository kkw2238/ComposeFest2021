package com.example.basicscodelab

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.tooling.preview.Preview
import com.example.basicscodelab.ui.theme.BasicsCodelabTheme
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.compose.tutorial.SampleData

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*
          SetContent안에 XML 대신 레이아웃을 구성할 Composable함수를 들어갈 수 있다.

             => 현재는 XML이 아닌 Composable함수를 넣는 방식으로 UI를 구성
        */
        setContent {
            BasicsCodelabTheme{
                Conversation(SampleData.conversationSample)
            }
        }
    }
}

data class Message(val author : String, val body : String)

/*
    Reusing Composables

        - @Composable 어노테이션을 추가하여 함수를 분리하거나 독립적으로 편집이 가능 !
            => XML에서 <include>를 사용했던 것 처럼
 */

@Composable
fun MessageCard(msg: Message) {
    // 요소를 가로로 정렬
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource(R.drawable.profile_picture),
            contentDescription = "Contact profile picture",
            modifier = Modifier
                // 이미지 크게 조정
                .size(40.dp)
                // 이미지를 원 모양으로 조정
                .clip(CircleShape)
                // 이미지에 테두리 추가
                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
        )

        // 이미지와 행 사이에 여백을 준다.
        Spacer(modifier = Modifier.width(8.dp))

        /*
            remember를 통해 메모리에 로컬 상태를 저장하고 mutableStateOf에 전달된 값의 변경사항 추적 가능
            이 상태를 사용하는 컴포저블 및 하위 요소는 값이 업데이트되면 자동으로 갱신( 재구성 )
        */
        var isExpanded by remember{ mutableStateOf(false) }
        val surfaceColor : Color by animateColorAsState(
            if(isExpanded) MaterialTheme.colors.primary
            else MaterialTheme.colors.surface
        )

        // 요소를 수직으로 정렬
        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            Text(
                text = msg.author,
                // 제목 스타일 지정
                color = MaterialTheme.colors.secondaryVariant,
                // 서체 지정
                style = MaterialTheme.typography.subtitle2
            )
            // 메시지와 메시지 사이에 여백을 준다.
            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                shape = MaterialTheme.shapes.medium,
                elevation = 1.dp,
                color = surfaceColor,
                modifier = Modifier.animateContentSize().padding(1.dp)
            ) {
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(all = 4.dp),
                    // 확장된 상태인 경우 모든 메시지 내용을 표시하고 아닐 경우 첫 줄만 표시
                    maxLines = if(isExpanded) Int.MAX_VALUE else 1,
                    // 서체 지정
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}

@Preview(name = "Light Mode")
// 다크모드로 설정
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "DarkMode"
)
@Composable
fun PreviewMessageCard() {
    BasicsCodelabTheme {
        MessageCard(
            msg = Message("Colleague", "Hey, take a look at Jetpack Compose, it's a great!")
        )
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
    BasicsCodelabTheme {
        Greeting("Android")
    }
}

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

@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn {
        items(messages) {
                message -> MessageCard(message)
        }
    }
}

@Preview
@Composable
fun PreviewConversation() {
    BasicsCodelabTheme {
        Conversation(SampleData.conversationSample)
    }
}

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
    Persisting state
        - RememberSaveable
            => 화면을 전환하거나 테마를 바꾸게 될 경우,
               Activity가 재구성 되면서 기존 Remeber로 저장했던 부분이 날아갈 수 있기에,
               이 부분을 저장 해놓기 위해서 RememberSaveable를 사용하여 저장
 */