# DeepLinkSample

Sample code for using DeepLink in Android

# Getting Started

* Chúng ta cần tạo 1 đường link mà khi click vào đó có thể vào được trực tiếp phần nào trong ứng dụng của mình.
* Đường link này có thể chứa dữ liệu trong đó và sẽ được gửi tới ứng dụng thông qua Intent

## DeepLink structure

DeepLink dựa trên **intent-filter** để có thể lắng nghe những sự kiện của intent hệ thống phát ra và mở ra theo đúng **action** mà bạn chọn.

Như vậy việc triển khai của DeepLink sẽ diễn ra ở trong file AndroidManifest.xml như sau:

```
<activity
  android:name=".DetailActivity"
  android:label="@string/app_name"
  android:launchMode="singleTop">
  <intent-filter android:label="@string/detail_title">
    <action android:name="android.intent.action.VIEW" />

    <category android:name="android.intent.category.DEFAULT" />
    <category android:name="android.intent.category.BROWSABLE" />
    
    <data
      android:host="ohoangngocthai.github.io"
      android:pathPrefix="/DeepLinkSample"
      android:scheme="https" />
  </intent-filter>
  
  <intent-filter android:label="@string/app_name">
    <action android:name="android.intent.action.VIEW" />
  
    <category android:name="android.intent.category.DEFAULT" />
    <category android:name="android.intent.category.BROWSABLE" />
  
        <data
            android:scheme="com-access-store"
            android:host="thaihn" />
  </intent-filter>
</activity>
```
Ở đây Activity này xử lý 2 DeepLink khác nhau:
* https://ohoangngocthai.github.io/DeepLinkSample/
* com-access-store://thaihn?id=SHSA_ST01F0000023214P_57&name=HoangNgocThai

Trong đó cần chú ý đến thẻ **data** bao gồm:
* **scheme**: Là phần thiết yếu nhất để xác định một URI, ít nhất phải có một thuộc tính scheme nằm trong bộ lọc intent-filter. Có thể đặt là http để tiếp nhận link từ web.
* **host**: Thuộc tính này là phần xác định tên miền của đường link. Nó chỉ có ý nghĩa khi thuộc tính schema được thêm vào. Thông thường sẽ được chỉ định một cách tường mình, nhưng nếu bạn muốn khớp nhiều tên miền thì nên sử dụng dấu * để  tượng trưng cho một nhóm tên miền. Ví dụ như *google.com sẽ tượng trưng cho www.google.com, develop.google.com, ....
* **port**: Thuộc tính này tượng trung cho cổng của URI. Nó chỉ có ý nghĩa khi thuộc tính schema và host được chỉ định và thường được bắt đầu bằng **/**.
  * **path**: Ứng với đường dẫn hoàn chỉnh của đường link mà intent gửi đến
  * **pathPrefix**: Ứng với đường dẫn khớp với phần đầu của đường link.
  * **pathPattern**: Ứng với đường dẫn hoàn chỉnh của đường link nhưng mà nó có thể thêm ký hiệu để biểu tượng cho một ký tự nào đó. Ví dụ như nếu để là (*) thì đại diện cho một chuỗi từ 0 đến nhiều ký tự đã xuất hiện trước đó. Còn để là (.*) thì đại diện cho bất kì chuỗi nào từ 0 đến nhiều ký tự.

> Thông thường Action được để là **View** để tích hợp được việc tìm kiếm của Google. Category cũng thường được để mặc định là **DEFAULT** hoặc là **BROWSABLE**.
## Implement DeepLink

Việc triển khai DeepLink cần thông qua các bước sau:
1. Khai báo **intent-filter** ở trong Activity có thể lắng nghe được DeepLink. (Như ở phần trên)
2. Nhận dữ liệu trong Activity
Dữ liệu của data gửi về là một URI, vì vậy chúng ta sẽ lấy được các parameter cần thiết của DeepLink để xử lý bên trong Activity.

Nếu DeepLink có dạng Query như sau: com-access-store://thaihn?id=SHSA_ST01F0000023214P_57&name=HoangNgocThai
```
intent.data?.let {
    val id = it.getQueryParameter(PARAMETER_ID)
    val name = it.getQueryParameter(PARAMETER_NAME)
    val text = "$id --- $name"
    deeplinkBinding.tvId.text = text
}
```
Nếu DeepLink có dạng path như sau: https://ohoangngocthai.github.io/DeepLinkSample/1/thaihn
```
intent.data?.let {
    val value = StringBuilder()
    it.pathSegments.forEach {
    value.append("$it-")
    }
    deeplinkBinding.tvId.text = value
}
```

3. Kiểm tra xem đã xử lý đúng chưa bằng adb hoặc trực tiếp gửi link qua.
```
adb shell am start -a android.intent.action.VIEW -d "https://ohoangngocthai.github.io/DeepLinkSample/"
adb shell am start -a android.intent.action.VIEW -d "https://ohoangngocthai.github.io/DeepLinkSample/2/thaihn"
```

> Việc xử lý lấy dữ liệu này nên được thực hiện trong hàm onNewIntent() bởi vì khi được gọi lại nhiều lần mà không cần tạo ra một instance mới.

## DeepLink với Navigation Component
Navigation Component là công cụ quản lý ứng dụng của bạn thông qua các Fragment và chỉ có một Activity chính quản lý các destination của nó. Bạn có thể tìm hiểu thêm về nó ở [đây](https://developer.android.com/topic/libraries/architecture/navigation/navigation-implementing)

1. Thêm DeepLink vào trong Destination
Sử dụng code hoặc là giao diện trong file **nav_graph.xml** như sau:

```
<fragment
        android:id="@+id/contactFragment"
        android:name="android.thaihn.deeplinksample.navigation.fragment.ContactFragment"
        android:label="ContactFragment">
        <argument
            android:name="id"
            app:argType="integer" />
        <argument
            android:name="name"
            app:argType="string" />
        <deepLink app:uri="app-thaihn://thaihn.com.vn/{id}/{name}" />
        <action
            android:id="@+id/openDetail"
            app:destination="@id/detailFragment" />
    </fragment>
    <fragment
        android:id="@+id/detailFragment"
        android:name="android.thaihn.deeplinksample.navigation.fragment.DetailFragment"
        android:label="DetailFragment">
        <argument
            android:name="title"
            app:argType="string" />
        <deepLink app:uri="app-thaihn://thaihn.com.vn/?title={title}" />
    </fragment>
```

> Navigation Component cũng hỗ trợ việc sử dụng DeepLink với dạng **Query** hoặc là **Path** đối với URI được tạo.

2. Khai báo trong **AndroidManifest.xml**
Chúng ta phải khai báo trong Activity chứa Navigation Graph để có thể lắng nghe được:
```
<activity android:name=".navigation.NavigationActivity">
    <nav-graph android:value="@navigation/navigation_nav_graph"/>
</activity>
```
3. Nhận dữ liệu
Việc nhận dữ liệu từ DeepLink cũng sẽ đơn giản với **Plugin SafeArgs** hỗ trợ cùng với Navigation Component 
```
arguments?.let {
    val id = ContactFragmentArgs.fromBundle(it).id
    val name = ContactFragmentArgs.fromBundle(it).name

    val text = "$id -- $name"
    contactBinding.tvContact.text = text
}
```  

## DynamicLink của FireBase
DynamicLink FireBase là các link liên kết hoạt động theo cách bạn muốn, nó sử dụng được trên nhiều nền tảng và cho dù ứng dụng của bạn đã được cài đặt hay chưa.
Việc triển khai Dynamic Link sẽ như sau:
### Dynamic Links
1. Tạo project FireBase và cài đặt **Dynamic Link SDK** 
* Liên kết Project với FireBase có thể tham khảo ở [đây](https://firebase.google.com/docs/android/setup)
* Thêm dependencies của Dynamic Link vào app build.gradle
```
implementation 'com.google.firebase:firebase-invites:16.1.0'
implementation 'com.google.firebase:firebase-dynamic-links:16.1.7'
```
2. Tạo một **Dynamic Link**

> Tạo một Dynamic Link bạn cần truy cập vào FireBase console và vào phần **Grow/Dynamic Links** để tạo một URL prefix cho Link của bạn. Ở đây mình tạo lấy một domain như sau: https://thaihn.page.link
Sau đó bạn có thể tạo các DeepLink khác nhau ở trên FireBase console. Có thể lựa chọn mở trên trình duyệt hay từ một nền tảng khác như Android hay Ios.

* Sort Link mình tạo ra như sau: https://thaihn.page.link/dynamic
* Đây là link mình tạo demo: https://thaihn.vn/dynamic/1?name=HoangNgocThai

3. Xử lý **Dynamic Link** bên trong ứng dụng 

* Như vậy bạn chỉ cần xử lý nhận của Dynamic Link đối với link rút gọn ở trong **AndroidManifest.xml**
```
<intent-filter android:label="@string/dynamic_link_title">
    <action android:name="android.intent.action.VIEW" />

    <category android:name="android.intent.category.DEFAULT" />
    <category android:name="android.intent.category.BROWSABLE" />

    <data
        android:host="thaihn.page.link"
        android:pathPrefix="/dynamic"
        android:scheme="https" />
</intent-filter>
```

* Đọc dữ liệu ở trong Activity như sau: 
```
FirebaseDynamicLinks.getInstance()
    .getDynamicLink(intent)
    .addOnSuccessListener(this) {
        it?.link?.let { uri ->
            val name = uri.getQueryParameter(PARAMETER_NAME)
            val path = StringBuilder()
            uri.pathSegments?.forEach { it ->
                path.append(it)
            }
            dynamicLinkBinding.tvId.text = "name:$name - id:$path"
        }
    }
    .addOnFailureListener(this) {
        it.printStackTrace()
            dynamicLinkBinding.tvId.text = it.message
    }
```

4. Xem những dữ liệu tổng hợp trên FireBase console
Bạn có thể xem những dữ liệu thống kê về lượt click đầu tiên hoặc là các lần mở lại sau đó trên [FireBase console](https://console.firebase.google.com) 

### Invite Link
* Bạn có thể sử dụng Dynamic Link để chia sẻ với người khác sử dụng ứng dụng bằng cách tạo link invite của mình gửi đến bạn bè như sau:
```
fun generateContentLink(): Uri {
    val baseUrl = Uri.parse("https://thaihn.vn/invite?id=1111&name=NgocThai")
    val domain = "https://thaihn.page.link"

    val link = FirebaseDynamicLinks.getInstance()
        .createDynamicLink()
        .setLink(baseUrl)
        .setDomainUriPrefix(domain)
        .setIosParameters(DynamicLink.IosParameters.Builder("android.thaihn.deeplinksample").build())
        .setAndroidParameters(DynamicLink.AndroidParameters.Builder("android.thaihn.deeplinksample").build())
        .buildDynamicLink()

    return link.uri
}
```
* Sau đó xử lý việc lấy dữ liệu bên trong Activity muốn handle như sau:
```
FirebaseDynamicLinks.getInstance()
    .getDynamicLink(intent)
    .addOnSuccessListener(this) { data ->

        val deepLink = data.link
        val invite = FirebaseAppInvite.getInvitation(data)
        if (invite != null) {
            Log.d(TAG, "invite ${invite.toString()}")
            val id = invite.invitationId
            inviteLinkBinding.tvId.text = id
        }
    }
    .addOnFailureListener(this) {
        it.printStackTrace()
        inviteLinkBinding.tvId.text = it.toString()
    }
```
### Reward referrals
Một trong những chức năng hay của Dynamic Link là việc xử lý giới thiệu người và thưởng cho người giới thiệu bạn cài ứng dụng.
1. Tạo Deeplink chứa id của người giới thiệu có dạng như sau:
```
https://mygame.example.com/?invitedby=SENDER_UID
```
Để tạo được link trên, chúng ta xử dụng trong code như sau:
```
val user = FirebaseAuth.getInstance().currentUser
val uid = user!!.uid
val link = "https://mygame.example.com/?invitedby=$uid"
FirebaseDynamicLinks.getInstance().createDynamicLink()
    .setLink(Uri.parse(link))
    .setDomainUriPrefix("https://example.page.link")
    .setAndroidParameters(
        DynamicLink.AndroidParameters.Builder("com.example.android")
            .setMinimumVersion(125)
            .build())
    .setIosParameters(
        DynamicLink.IosParameters.Builder("com.example.ios")
            .setAppStoreId("123456789")
            .setMinimumVersion("1.0.1")
            .build())
    .buildShortDynamicLink()
    .addOnSuccessListener { shortDynamicLink ->
        mInvitationUrl = shortDynamicLink.shortLink
            // ...
}
```
2. Gửi đường link đã tạo cho người dùng khác
Bạn có thể gửi link qua Email, SMS, Facebook, ... tùy thuộc vào ứng dụng có trong máy của bạn.
Dưới đây mô tả việc gửi mail để giới thiệu người cài ứng dụng:
```
val referrerName = FirebaseAuth.getInstance().currentUser?.displayName
val subject = String.format("%s wants you to play MyExampleGame!", referrerName)
val invitationLink = mInvitationUrl.toString()
val msg = "Let's play MyExampleGame together! Use my referrer link: $invitationLink"
val msgHtml = String.format("<p>Let's play MyExampleGame together! Use my " +
        "<a href=\"%s\">referrer link</a>!</p>", invitationLink)

val intent = Intent(Intent.ACTION_SENDTO)
intent.data = Uri.parse("mailto:") // only email apps should handle this
intent.putExtra(Intent.EXTRA_SUBJECT, subject)
intent.putExtra(Intent.EXTRA_TEXT, msg)
intent.putExtra(Intent.EXTRA_HTML_TEXT, msgHtml)
if (intent.resolveActivity(packageManager) != null) {
    startActivity(intent)
}
```
3. Nhận dữ liệu trong activity
Nhận dữ liệu ở đây để lấy được id của người đã gửi cho mình lời giới thiêu, sau đó xử lý việc lưu lại và tặng thưởng, ...

```
FirebaseDynamicLinks.getInstance()
    .getDynamicLink(intent)
    .addOnSuccessListener(this) { pendingDynamicLinkData ->
        // Get deep link from result (may be null if no link is found)
        var deepLink: Uri? = null
        if (pendingDynamicLinkData != null) {
            deepLink = pendingDynamicLinkData.link
        }
        // If the user isn't signed in and the pending Dynamic Link is
        // an invitation, sign in the user anonymously, and record the
        // referrer's UID.
        //
        val user = FirebaseAuth.getInstance().currentUser
        if (user == null && deepLink != null && deepLink.getBooleanQueryParameter("invitedby", false)) {
            val referrerUid = deepLink.getQueryParameter("invitedby")
            createAnonymousAccountWithReferrerInfo(referrerUid)
        }
    }
}
```

