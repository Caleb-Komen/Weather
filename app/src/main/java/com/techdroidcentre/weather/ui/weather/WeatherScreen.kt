package com.techdroidcentre.weather.ui.weather

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.techdroidcentre.weather.R
import com.techdroidcentre.weather.core.model.CurrentWeather
import com.techdroidcentre.weather.core.model.DailyWeather
import com.techdroidcentre.weather.core.model.HourlyWeather
import com.techdroidcentre.weather.core.model.TemperatureInfo
import com.techdroidcentre.weather.core.model.Weather
import com.techdroidcentre.weather.core.model.WeatherInfo
import com.techdroidcentre.weather.ui.theme.WeatherTheme

@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier,
    viewModel: WeatherScreenViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    WeatherScreen(
        uiState = uiState,
        modifier = modifier
    )
}

@Composable
fun WeatherScreen(
    uiState: WeatherScreenUiState,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            item {
                uiState.weather?.currentWeather?.let { currentWeather->
                    CurrentWeatherCard(currentWeather = currentWeather)
                }
            }
            item {
                uiState.weather?.hourlyWeather?.let { hourlyWeather->
                    HourlyWeatherCollection(hourlyWeather = hourlyWeather)
                }
            }
            uiState.weather?.dailyWeather?.let { dailyWeather->
                dailyWeatherCollection(dailyWeather = dailyWeather)
            }
        }
        if (uiState.error.isNotEmpty()) {
            Text(
                text = uiState.error,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
        }
        if (uiState.isLoading) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun CurrentWeatherCard(
    currentWeather: CurrentWeather,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = "Nairobi",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(currentWeather.weatherInfo[0].icon)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(if (isSystemInDarkTheme()) R.drawable.black_background else R.drawable.white_background),
                    error = painterResource(R.drawable.error_image),
                    contentDescription = null,
                    modifier = Modifier.size(96.dp)
                )
                Spacer(Modifier.width(16.dp))
                Text(
                    text = "${currentWeather.temperature}",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.width(16.dp))
                Column {
                    Text(
                        text = "Feels like: ${currentWeather.feelsLike}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Humidity: ${currentWeather.humidity}%",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Wind Speed: ${currentWeather.windSpeed}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
fun HourlyWeatherCollection(
    hourlyWeather: List<HourlyWeather>,
    modifier: Modifier = Modifier
) {
    val lastIndex = hourlyWeather.size - 1
    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(top = 8.dp, bottom = 16.dp)
    ) {
        itemsIndexed(items = hourlyWeather.take(12)) { index, weather ->
            HourlyWeatherItem(hourlyWeather = weather)
            if (index < lastIndex) Spacer(Modifier.width(8.dp))
        }
    }
}

@Composable
fun HourlyWeatherItem(
    hourlyWeather: HourlyWeather,
    modifier: Modifier = Modifier
) {
    Surface(modifier = modifier.width(72.dp)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.padding(8.dp)
        ) {
            Text(
                text = hourlyWeather.time
            )
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(hourlyWeather.weatherInfo[0].icon)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(if (isSystemInDarkTheme()) R.drawable.black_background else R.drawable.white_background),
                error = painterResource(R.drawable.error_image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${hourlyWeather.temperature}"
            )
        }
    }
}

fun LazyListScope.dailyWeatherCollection(
    dailyWeather: List<DailyWeather>
) {
    item {
        Text(
            text = "Forecast for next days",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }

    items(items = dailyWeather) { weather ->
        DailyWeatherItem(dailyWeather = weather)
    }
}

@Composable
fun DailyWeatherItem(
    dailyWeather: DailyWeather,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = dailyWeather.time,
            modifier = Modifier.weight(1f)
        )
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(dailyWeather.weatherInfo[0].icon)
                .crossfade(true)
                .build(),
            placeholder = painterResource(if (isSystemInDarkTheme()) R.drawable.black_background else R.drawable.white_background),
            error = painterResource(R.drawable.error_image),
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
        )
        Text(
            text = "${dailyWeather.temperatureInfo.min}",
            modifier = Modifier.weight(2f),
            textAlign = TextAlign.Center
        )
        Text(
            text = "${dailyWeather.temperatureInfo.max}",
            modifier = Modifier.weight(2f),
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun WeatherScreenPreview() {
    val weatherInfo = WeatherInfo(1, "Rain", "Light rains", "icon")
    val currentWeather = CurrentWeather(23.3f, 24.0f, 78, 23f, listOf(weatherInfo))
    val hourlyWeather = listOf(
        HourlyWeather("08:00", 24.0f, listOf(weatherInfo)),
        HourlyWeather("09:00", 23.78f, listOf(weatherInfo)),
        HourlyWeather("10:00", 23.98f, listOf(weatherInfo))
    )
    val dailyWeather = listOf(
        DailyWeather("Mon",TemperatureInfo(23.45f, 24.75f), listOf(weatherInfo)),
        DailyWeather("Tue", TemperatureInfo(21.3f, 22.01f), listOf(weatherInfo)),
        DailyWeather("Wed", TemperatureInfo(22.4f, 23.56f), listOf(weatherInfo))
    )
    WeatherTheme {
        WeatherScreen(
            WeatherScreenUiState(
                weather = Weather(currentWeather, hourlyWeather, dailyWeather)
            )
        )
    }
}