package com.aurelio.baldor.feature_home.components.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.aurelio.baldor.core.R

data class ChildrenDto(val name: String, val grade: String, val gender: String, val id: Int)

@Composable
fun ChildrenCard(child: ChildrenDto, selected: Boolean){
    Card(
        modifier = Modifier
            .width(102.dp)
            .height(97.dp)
            .padding(4.dp),
        colors = CardDefaults.cardColors(containerColor = White),
    ) {
        Column(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(if (child.gender == "M") R.drawable.profile_male else R.drawable.profile_female),
                contentDescription = "Profile",
                modifier = Modifier
                    .size(45.dp)
                    .clip(CircleShape)                       // clip to the circle shape
                    .border(
                        width = if(selected) 2.dp else 0.dp,
                        color = if(selected) colorResource(R.color.secondary) else White,
                        shape = CircleShape
                    ).padding(5.dp)
            )
            Text(
                child.name,
                style = MaterialTheme.typography.bodyMedium,
                color = colorResource(if(selected) R.color.text_primary else R.color.text_secondary),
                fontWeight = if(selected) FontWeight.Bold else FontWeight.Normal
            )
            Text(
                child.grade,
                style = MaterialTheme.typography.bodyMedium,
                color = colorResource(if(selected) R.color.text_primary else R.color.text_secondary)
            )
        }
    }
}