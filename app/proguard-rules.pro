# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-keep public class com.pegbeer.domain.model.*{
    public *;
}

-keepattributes Signature,InnerClasses, EnclosingMehthod

-keepattributes AnnotationDefault

-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

-keep class android.** { *; }
-keep interface android.** { *; }

-keep class com.pegbeer.buyer.** { *; }
-keep interface com.pegbeer.buyer.** { *; }

-keep class com.pegbeer.domain.** { *; }
-keep interface com.pegbeer.domain.** { *; }

-keep class com.pegbeer.infrastructure.** { *; }
-keep interface com.pegbeer.infrastructure.** { *; }


#-dontwarn retrofit2.KotlinExtensions
#-dontwarn retrofit2.KotlinExtensions$*

-dontwarn javax.annotation.*
-dontwarn javax.lang.model.element.Modifier

-dontwarn retrofit.**
-keep class retrofit.** { *; }

-dontwarn com.microsoft.appcenter.*

#-if interface * { @retrofit2.http.* <methods>; }
#-keep,allowobfuscation interface <1>

#-keep,allowobfuscation,allowshrinking interface retrofit2.Call
#-keep,allowobfuscation,allowshrinking class retrofit2.Response

-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation

-dontshrink
-dontoptimize

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
#-keep class com.google.gson.examples.android.model.** { *; }

##---------------End: proguard configuration for Gson  ----------