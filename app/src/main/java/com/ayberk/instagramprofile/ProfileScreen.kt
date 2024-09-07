package com.ayberk.instagramprofile

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.grid.GridCells as GridCells

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProfileScreen() {

    var selectedTabIndex by remember {
        mutableStateOf(0)
    }
    var additionalDescription by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
      //  TopBar(name = "Ayberk Temin", modifier = Modifier.padding(10.dp), onDescriptionChange = { newDescription -> additionalDescription = newDescription })
        Spacer(modifier = Modifier.height(4.dp))
        ProfileSection(modifier = Modifier.fillMaxWidth(), additionalDescription = additionalDescription)
        Spacer(modifier = Modifier.height(25.dp))
        ButtonSection(modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(25.dp))
        HighlightSection(
            highlights = listOf(
                ImageWithText(
                    image = painterResource(id = R.drawable.hsd),
                    text = "HSD Ambassador",
                ),
                ImageWithText(
                    image = painterResource(id = R.drawable.gdg),
                    text = "GDG Kutahya Organizer",

                ),
                ImageWithText(
                    image = painterResource(id = R.drawable.bbtlogo),
                    text = "BBT President",

                ),
                ImageWithText(
                    image = painterResource(id = R.drawable.hdg),
                    text = "HDG Organizer",

                ),
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.height(25.dp))
        PostTabView(
            imageWithTexts = listOf(
                ImageWithText(
                    image = painterResource(id = R.drawable.bar),
                    text = "Skills",
                ),
                ImageWithText(
                    image = painterResource(id = R.drawable.profile),
                    text = "Profile",
                ),
            )
        ) {
            selectedTabIndex = it
        }
        when(selectedTabIndex) {
            0 -> PostSection(
                posts = listOf(
                    painterResource(id = R.drawable.kotlin),
                    painterResource(id = R.drawable.firebase),
                    painterResource(id = R.drawable.android),
                    painterResource(id = R.drawable.compose),
                    painterResource(id = R.drawable.hms),
                    painterResource(id = R.drawable.git),
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun TopBar(
    name: String,
    modifier: Modifier = Modifier,
    onDescriptionChange: (String) -> Unit
) {
    val context = LocalContext.current
    val preferencesHelper = remember { PreferencesHelper(context) }
    var expanded by remember { mutableStateOf(false) }
    var additionalDescription by remember { mutableStateOf(preferencesHelper.getDescription()) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            tint = Color.Black,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = name,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Box {
            Icon(
                painter = painterResource(id = R.drawable.ic_dotmenu),
                contentDescription = "Dot Menu",
                tint = Color.Black,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { expanded = true }
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                offset = DpOffset(
                    x = (-20).dp,
                    y = 0.dp
                )
            ) {
                MenuItems(
                    onDismiss = { expanded = false },
                    onAddClick = { newDescription ->
                        additionalDescription = "$additionalDescription\n- $newDescription"
                        preferencesHelper.saveDescription(additionalDescription) // Açıklamayı kaydet
                        onDescriptionChange(additionalDescription) // Eklenen açıklamayı dışarı aktarma
                    },
                    onDeleteClick = {
                        // Silme işlemini gerçekleştirmek için buraya kod ekleyin
                        // Silinmiş açıklamaları güncelleme işlemi
                        additionalDescription = ""
                        preferencesHelper.saveDescription(additionalDescription)
                        onDescriptionChange(additionalDescription)
                    }
                )
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuItems(
    onDismiss: () -> Unit,
    onAddClick: (String) -> Unit, // Eklenen metni `ProfileSection`'a iletmek için callback
    onDeleteClick: () -> Unit
    ) {
    // Dialog state'lerini tanımlıyoruz
    var isDialogOpen by remember { mutableStateOf(false) }
    var inputText by remember { mutableStateOf("") }
    var showConfirmDeleteDialog by remember { mutableStateOf(false) }

    DropdownMenuItem(
        text = { Text("Ekle") },
        onClick = { isDialogOpen = true }
    )
    DropdownMenuItem(
        text = { Text("Düzenle") },
        onClick = { onDismiss() }
    )
    DropdownMenuItem(
        text = { Text("Sil") },
        onClick = {   showConfirmDeleteDialog = true  }
    )

    // AlertDialog'u oluşturuyoruz
    if (isDialogOpen) {
        AlertDialog(
            onDismissRequest = {
                isDialogOpen = false // Dialog kapatılacak
            },
            confirmButton = {
                Button(onClick = {
                    // Kullanıcının yazdığı metni işle
                    if (inputText.isNotEmpty()) {
                        onAddClick(inputText) // Metni callback aracılığıyla gönder
                        inputText = "" // Girişi temizle
                    }
                    isDialogOpen = false // Dialog'u kapat
                }) {
                    Text("Ekle")
                }
            },
            dismissButton = {
                Button(onClick = {
                    isDialogOpen = false // Dialog'u kapat
                }) {
                    Text("İptal")
                }
            },
            title = { Text("Profil Açıklaması Ekle:") },
            text = {
                TextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    placeholder = { Text("Açıklama giriniz...") }
                )
            }
        )
    }
    if (showConfirmDeleteDialog) {
        AlertDialog(
            onDismissRequest = {
                showConfirmDeleteDialog = false
            },
            confirmButton = {
                Button(onClick = {
                    onDeleteClick() // Silme işlemini gerçekleştirme
                    showConfirmDeleteDialog = false
                }) {
                    Text("Evet")
                }
            },
            dismissButton = {
                Button(onClick = {
                    showConfirmDeleteDialog = false
                }) {
                    Text("Hayır")
                }
            },
            title = { Text("Silme Onayı") },
            text = { Text("Eklenen açıklamaları silmek istediğinizden emin misiniz?") }
        )
    }
}

@Composable
fun ProfileSection(
    modifier: Modifier = Modifier,
    additionalDescription: String // Bu parametreyi ekleyin
) {
    var isProfileImageClicked by remember { mutableStateOf(false) }
    var isMenuExpanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val preferencesHelper = remember { PreferencesHelper(context = context) }
    var expanded by remember { mutableStateOf(false) }
    var additionalDescription by remember { mutableStateOf(preferencesHelper.getDescription()) }
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        TopBar(
            name = "Ayberk Temin",
            modifier = Modifier.padding(10.dp),
            onDescriptionChange = { newDescription ->
                additionalDescription = newDescription
            }
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            RoundImage(
                image = painterResource(id = R.drawable.ayberk),
                modifier = Modifier
                    .size(100.dp)
                    .weight(3f)
                    .clickable {
                        isProfileImageClicked = true
                    }
            )
            Spacer(modifier = Modifier.width(16.dp))
            StatSection(modifier = Modifier.weight(7f))
        }

        ProfileDescription(
            displayName = "Android Developer",
            description = "- I have been working in the field of mobile applications for about 2 years.\n" +
                    "- I want to improve myself in this field." +
                    additionalDescription,
            experienceBy = listOf("Huawei", ", Markakod Reklam Tasarım ve Yazılım Hizmetleri"),
            otherCount = 1
        )

        if (isProfileImageClicked) {
            AlertDialog(
                onDismissRequest = {
                    isProfileImageClicked = false
                },
                confirmButton = {
                    Image(
                        painter = painterResource(id = R.drawable.ayberk),
                        contentDescription = null,
                        modifier = Modifier
                            .size(250.dp)
                            .clip(CircleShape)
                    )
                },
                containerColor = Color.Transparent,
            )
        }
    }
}

@Composable
fun RoundImage(
    image: Painter,
    modifier: Modifier = Modifier
) {
    Image(
        painter = image,
        contentDescription = null,
        modifier = modifier
            .aspectRatio(1f, matchHeightConstraintsFirst = true)
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = CircleShape
            )
            .padding(3.dp)
            .clip(CircleShape)
    )
}

@Composable
fun StatSection(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier
    ){
        ProfileStat(numberText = "31", text ="Project")
        ProfileStat(numberText = "28", text ="Kotlin")
        ProfileStat(numberText = "3", text ="Other Technologies")
    }
}

@Composable
fun ProfileStat(
    numberText:String,
    text:String,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = numberText,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = text)
    }
}

@Composable
fun ProfileDescription(
    displayName : String,
    description : String,
    experienceBy : List<String>,
    otherCount : Int
) {
    val letterSpacing = 0.5.sp
    val lineHeight = 20.sp
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Text(
            text = displayName,
            fontWeight = FontWeight.Bold,
            letterSpacing = letterSpacing,
            lineHeight = lineHeight
        )
        Text(
            text = description,
            letterSpacing = letterSpacing,
            lineHeight = lineHeight
        )

        if (experienceBy.isNotEmpty()){
            Text(text = buildAnnotatedString {
                val boldStyle = SpanStyle(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                append("Experience ")
                experienceBy.forEachIndexed { index, name ->
                    pushStyle(boldStyle)
                    append(name)
                    pop()
                    if (index < experienceBy.size - 1){
                        append("","")
                    }
                }
                if (otherCount > 0){
                    append(" and ")
                    pushStyle(boldStyle)
                    append("$otherCount others")
                }
            },
                letterSpacing = letterSpacing,
                lineHeight = lineHeight
            )
        }
    }
}

@Composable
fun ButtonSection(
    modifier: Modifier = Modifier
) {
    val minWidth = 95.dp
    val height = 30.dp
    Row (
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
    ){
        ActionButton(
            text = "CV",
            icon = R.drawable.cv,
            onClick = {},
            Url = "https://drive.google.com/file/d/11wdZrwgvMiiFwElD2All36_k_5IoXsMx/view?usp=sharing",
            modifier = Modifier
                .defaultMinSize(minWidth = minWidth)
                .height(height)
        )
        ActionButton(
            text = "Github",
            icon = R.drawable.github,
            onClick = {},
            Url = "https://github.com/ayberktmn",
            modifier = Modifier
                .defaultMinSize(minWidth = minWidth)
                .height(height)
        )
        ActionButton(
            text = "Linkedln",
            icon = R.drawable.linkedin,
            onClick = {

            },
            Url = "https://www.linkedin.com/in/ayberk-temin",
            modifier = Modifier
                .defaultMinSize(minWidth = minWidth)
                .height(height)
        )
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    text: String? = null,
    icon: Int,
    onClick: () -> Unit = {},
    Url: String? = null
) {
    val context = LocalContext.current
    Row (
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickable {
                Url?.let { url ->
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    context.startActivity(intent)
                }
            }
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(5.dp)
            )
            .padding(6.dp)
    ){
        if (text != null){
            Text(
                text = text,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
                )
        }
        if (icon != null){
            val painter = painterResource(id = icon)
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                painter = painter ,
                contentDescription = null,
            )
        }
    }
}

@Composable
fun HighlightSection(
    modifier: Modifier = Modifier,
    highlights: List<ImageWithText>,
) {
    var selectedItemIndex by remember { mutableStateOf(-1) }
    LazyRow(modifier = modifier) {
        items(highlights.size) { index ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(end = 15.dp)
            ) {
                RoundImage(
                    image = highlights[index].image,
                    modifier = Modifier
                        .size(70.dp)
                        .clickable {
                            selectedItemIndex = index
                        }
                )
                Text(
                    text = highlights[index].text,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
    if (selectedItemIndex != -1 && selectedItemIndex < highlights.size) {
        // Alert Dialog'ı göster
        AlertDialog(
            onDismissRequest = {
                selectedItemIndex = -1 // Dialog kapatıldığında selectedItemIndex'i sıfırla
            },
            containerColor = Color.Transparent,
            confirmButton = {
                    when (selectedItemIndex) {
                        0 -> {
                            Image(
                                painter = painterResource(id = R.drawable.hsdambassador),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(500.dp)
                                    .clip(RoundedCornerShape(16.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }
                        1 -> {
                            Image(
                                painter = painterResource(id = R.drawable.gdgorganizer),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(500.dp)
                                    .clip(RoundedCornerShape(16.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }
                        2 -> {
                            Image(
                                painter = painterResource(id = R.drawable.bbtpresident),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(500.dp)
                                    .clip(RoundedCornerShape(16.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }
                        3 -> {
                            Image(
                                painter = painterResource(id = R.drawable.mezunlarkonusuyor4),  // resim değiştirilecek
                                contentDescription = null,
                                modifier = Modifier
                                    .size(500.dp)
                                    .clip(RoundedCornerShape(16.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }
                        // Daha fazla durumu ekleyebilirsiniz
                        else -> {
                            // Diğer durumlar
                            println("Tıklanan Diğer Öğe")
                        }
                    }
            }
        )
    }
}



@Composable
fun PostTabView(
    modifier: Modifier = Modifier,
    imageWithTexts: List<ImageWithText>,
    onTabSelected: (selectedIndex: Int) -> Unit
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val inactiveColor = Color(0xFF777777)

    Column {
        // TabRow
        TabRow(
            selectedTabIndex = selectedTabIndex,
            contentColor = Color.Black,
            modifier = modifier
        ) {
            imageWithTexts.forEachIndexed { index, item ->
                Tab(
                    selected = selectedTabIndex == index,
                    selectedContentColor = Color.Black,
                    unselectedContentColor = inactiveColor,
                    onClick = {
                        selectedTabIndex = index
                        onTabSelected(index)
                    }) {
                    Icon(
                        painter = item.image,
                        contentDescription = item.text,
                        tint = if (selectedTabIndex == index) Color.Black else inactiveColor,
                        modifier = Modifier
                            .padding(10.dp)
                            .size(20.dp)
                    )
                }
            }
        }

        LazyColumn {
            item {
                if (selectedTabIndex == 1) {
                    Text(
                        text = "Android Developer",
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .background(Color.Gray)
                            .padding(8.dp),
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp)
                    ) {
                        Image(
                            painterResource(id = R.drawable.huawei),
                            contentDescription = "huawei",
                            modifier = Modifier
                                .size(170.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Image(
                            painterResource(id = R.drawable.markakod),
                            contentDescription = "markakod",
                            modifier = Modifier
                                .size(170.dp)
                                .padding(start = 20.dp)
                        )
                    }
                }
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun PostSection(
    posts: List<Painter>,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier
            .scale(1.01f)
    ) {
        items(posts.size) {
            Image(
                painter = posts[it],
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .aspectRatio(1f)
                    .border(
                        width = 1.dp,
                        color = Color.White
                    )
            )
        }
    }
}
