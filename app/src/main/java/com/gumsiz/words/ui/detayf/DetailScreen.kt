package com.gumsiz.words.ui.detayf

import android.annotation.SuppressLint
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.UnderlineSpan
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.gumsiz.shared.data.model.WordModel
import com.gumsiz.words.ui.theme.WordsTheme
import org.koin.androidx.compose.koinViewModel
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetailScreen(wordId: String = "", navController: NavController) {
    val viewModel = koinViewModel<DetailViewModel>()
    val favorite by viewModel.favUpdate.observeAsState()
    val wordDB by viewModel.getVerb(wordId).collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = wordDB?.name?.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                            Locale.getDefault()
                        ) else it.toString()
                    }
                        ?: "Verben")
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            wordDB?.let {
                                it.favorite = !it.favorite
                                viewModel.addFav(it)
                            }
                        },
                    ) {
                        if (favorite == null) {
                            wordDB?.let {
                                Icon(
                                    imageVector = if (it.favorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                    tint = if (it.favorite) Color.Red else Color.Black,
                                    contentDescription = ""
                                )
                            }
                        } else {
                            Icon(
                                imageVector = if (favorite == 1) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                tint = if (favorite == 1) Color.Red else Color.Black,
                                contentDescription = ""
                            )
                        }
                    }
                },
                contentColor = Color.Black
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxSize()
                    .background(color = Color.White)
                    .verticalScroll(rememberScrollState())
            ) {
                val openDialog = remember { mutableStateOf(false) }
                wordDB?.let { word ->
                    Spacer(modifier = Modifier.padding(4.dp))
                    DetailBox("Übersetzung", true, openDialog = { openDialog.value = true }) {
                        Translate(
                            word = word
                        )
                    }
                    Spacer(modifier = Modifier.padding(4.dp))
                    DetailBox("Konjugationen") { Conjugation(word) }
                    Spacer(modifier = Modifier.padding(4.dp))
                    DetailBox("Strukturen") { Structure(word) }
                    Spacer(modifier = Modifier.padding(4.dp))
                    DetailBox("Beispiele") { Examples(word) }

                    if (openDialog.value) {
                        TranslateAddDialog(word = word, updateData = viewModel::doAction) {
                            openDialog.value = false
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun DetailBox(
    title: String,
    hasButton: Boolean = false,
    openDialog: (() -> Unit)? = null,
    content: @Composable () -> Unit?
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colors.secondary)
                .fillMaxWidth()
        ) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = title, modifier =
                    Modifier
                        .background(MaterialTheme.colors.primary)
                        .padding(8.dp), style = MaterialTheme.typography.h6
                )
                if (hasButton) FloatingActionButton(
                    onClick = { openDialog?.invoke() },
                    backgroundColor = MaterialTheme.colors.primary,
                    shape = MaterialTheme.shapes.small.copy(bottomStart = CornerSize(50))
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        tint = Color.Green,
                        contentDescription = ""
                    )
                }
            }

            Box(Modifier.padding(horizontal = 8.dp)) {
                content()
            }
        }
    }
}

@Composable
fun Translate(word: WordModel) {
    Column {
        for (translation in word.translation) {
            val text = "- $translation"
            Text(text = text, modifier = Modifier.padding(bottom = 4.dp))
        }
    }
}

@Composable
fun Conjugation(word: WordModel) {
    Column(modifier = Modifier) {
        if (word.firstSg!!.trim().isNotEmpty()) {
            val ss = SpannableStringBuilder("Ich ")
            ss.append(word.firstSg)
            ss.setSpan(
                UnderlineSpan(),
                ss.length - word.firstSg!!.length,
                ss.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            Text(text = ss.toString())
        }
        if (word.secondSg!!.trim().isNotEmpty()) {
            val ss = SpannableStringBuilder("Du ")
            ss.append(word.secondSg)
            ss.setSpan(
                UnderlineSpan(),
                ss.length - word.secondSg!!.length,
                ss.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            Text(text = ss.toString())
        }
        if (word.imp!!.trim().isNotEmpty()) {
            val ss = SpannableStringBuilder(word.imp)

            ss.setSpan(UnderlineSpan(), 0, ss.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            ss.append("  (Imperativ)")
            Text(text = ss.toString())
        }
        if (word.pret!!.trim().isNotEmpty()) {
            val ss = SpannableStringBuilder("Ich / Er-Sie-Es ")
            ss.append(word.pret)
            ss.setSpan(
                UnderlineSpan(),
                ss.length - word.pret!!.length,
                ss.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            ss.append(" (Prät.)")
            Text(text = ss.toString())
        }
        if (word.perfSg!!.trim().isNotEmpty()) {
            val ss = SpannableStringBuilder("Ich ")
            ss.append(word.perfSg)
            ss.setSpan(
                UnderlineSpan(),
                ss.length - word.perfSg!!.length,
                ss.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            ss.append(" (Perf.)")
            Text(text = ss.toString())
        }
        if (word.konj2FSg!!.trim().isNotEmpty()) {
            val ss = SpannableStringBuilder(word.konj2FSg)

            ss.setSpan(UnderlineSpan(), 0, ss.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            ss.append("  (Konjunktiv 2)")
            Text(text = ss.toString())
        }
    }
}

@Composable
fun Structure(word: WordModel) {
    Column {
        for (structure in word.structure!!) {
            val text = "- $structure"
            Text(text = text)
        }
    }
}

@Composable
fun Examples(word: WordModel) {
    Column {
        for (i in word.sampleSentence!!) {
            var text: String
            val text1 = ""
            if (i!!.trim().isNotEmpty()) {
                val wordSample = i.trim().replace("&#8211;", " - ", false)
                text = "* $wordSample"
                Text(text = text)
                Text(text = text1)
            }
        }
    }
}

@Composable
fun TranslateAddDialog(
    word: WordModel,
    updateData: (wordDB: WordModel) -> Unit,
    dismiss: () -> Unit
) {
    val newTranslation = remember {
        mutableStateOf("")
    }
    AlertDialog(
        onDismissRequest = dismiss,
        text = {
            Column {
                Text(text = "Add Translation", Modifier.padding(vertical = 8.dp))

                if (word.translation.size > 0) {
                    for (translation in word.translation) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "$translation")
                            IconButton(onClick = {
                                word.translation.remove(translation)
                                updateData(word)
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    tint = Color.Red,
                                    contentDescription = ""
                                )
                            }
                        }
                    }
                }
                TextField(
                    value = newTranslation.value,
                    onValueChange = {
                        newTranslation.value = it
                    },
                    singleLine = true,
                )
            }
        },
        buttons = {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                TextButton(onClick = {
                    if (newTranslation.value.isNotBlank()) {
                        word.translation.add(newTranslation.value)
                        updateData(word)
                        dismiss()
                    }
                }) {
                    Text(text = "Add")
                }
                TextButton(onClick = dismiss) {
                    Text(text = "Close")
                }
            }
        }
    )
}

@Preview
@Composable
fun DetailScreenPreview() {
    WordsTheme {
        DetailScreen(
            wordId = "",
            navController = rememberNavController()
        )
    }
}