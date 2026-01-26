# Правила ProGuard/R8 для приложения FreeNic
# Общая логика:
# - Debug: без обфускации (см. app/build.gradle.kts)
# - Release: включены обфускация и shrinkResources, задействован default optimize конфиг

# Сохраняем важные атрибуты, необходимые для аннотаций/Hilt/Compose/Kotlin
-keepattributes *Annotation*,EnclosingMethod,InnerClasses,Signature,SourceFile,LineNumberTable

# Hilt / Dagger
-dontwarn dagger.**
-dontwarn javax.inject.**
# Классы с аннотациями Hilt как правило корректно обрабатываются R8, однако
# сохраним сгенерированные компоненты и entry points, если на них идёт рефлексия
-keep class dagger.hilt.internal.** { *; }
-keep class hilt_aggregated_deps.** { *; }
-keep class dagger.hilt.android.internal.managers.** { *; }

# AndroidX / Lifecycle / ViewModel (имена ViewModel могут использоваться в SavedStateHandle)
-keep class androidx.lifecycle.** { *; }
-dontwarn androidx.lifecycle.**

# Jetpack Compose (как правило не требует правил, но избежим предупреждений)
-dontwarn androidx.compose.**

# Kotlin / Coroutines
-dontwarn kotlinx.coroutines.**

# Firebase Analytics (библиотека сама поставляет правила, добавим для тишины лога)
-dontwarn com.google.firebase.**
-dontwarn com.google.android.gms.**

# Google Play Billing
-dontwarn com.android.billingclient.**

# DataStore
-dontwarn androidx.datastore.preferences.**

# Сохраняем все классы, явно помеченные @Keep
-keep @androidx.annotation.Keep class * { *; }

# Если используется рефлексия над вашими моделями/DTO, добавьте их сюда, например:
# -keep class com.freenic.** { *; }

# WebView JS интерфейсы (пример, раскомментируйте при необходимости)
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#    public *;
#}