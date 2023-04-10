[![CircleCI](https://circleci.com/gh/circleci/circleci-docs.svg?style=svg)](https://https://github.com/kosenda/MultiColorPicker)

## 概要
<img src="https://user-images.githubusercontent.com/60963155/227688048-2d858dd1-a7e9-4037-b35c-f8ce0ca6379c.png" height="150" width="150">
Google Playで公開しているアプリ「マルチカラーピッカー」のソースになります。

https://play.google.com/store/apps/details?id=kosenda.makecolor

## アプリについての簡単な説明
- 様々な方法で色を作成できることだけでなく、保存や比較もできるアプリ
- アーキテクチャはMVVM
- 13言語対応
- 色についてはRGB, CMYK, HSV, HEXに対応


## 今後特にしていきたいこと
- マルチモジュール対応
- テストの追加

## アプリを初めて起動するとき
#### `local.properties`に以下を追記してください
```
adApplicationId=ca-app-pub-3940256099942544~3347511713
adUnitId=ca-app-pub-3940256099942544/6300978111
```


## 使用している代表的なライブラリ  
|名前|簡単な説明|
|:--|:--|
|Jetpack Compose|UIアプリ開発ツールキット|
|Material3|デザイン|
|kotlinx serialization|シリアライザー|
|Preferences DataStore|永続化データ|
|Room|データベース|
|Truth|アサーション|
|Hilt|依存性注入|
|Timber|ログ出力|
|compose-color-picker|カラーピッカー|
|palette-ktx|パレットAPI|
|play-services-ads|Google AdMob|
|play-services-oss-licenses|ライセンス|
|ui-text-google-fonts|ダウンロード可能なフォント|
|ktlint|フォーマッター|
|Circle Ci|CI/CD|
|danger|プルリク時に警告|

## モジュール構成
#### ⚠️ :feature と :core 内の依存関係は省略しています
```mermaid
flowchart LR
%%{init: {'flowchart' : {'curve' : 'linear'}}}%%

  classDef appModule fill:#AEFFDA,color:#000
  classDef featureModule fill:#FFDAAE,color:#000
  classDef coreModule fill:#DAAEFF,color:#000
  
  A1([:app]):::appModule --> B1([:feature:makecolor]):::featureModule
  A1 --> B2([:feature:settings]):::featureModule
  A1 --> B3([:feature:preview]):::featureModule
  A1 --> B4([:feature:info]):::featureModule
  
  B1 ----> C1([:core:model]):::coreModule 
  B1 ----> C2
  B1 ----> C5
  B1 ----> C7
  B1 ----> C8
  
  B2 ----> C2([:core:data]):::coreModule
  B2 ----> C5([:core:domain]):::coreModule
  B2 ----> C7([:core:ui]):::coreModule
  B2 ----> C8([:core:util]):::coreModule
  
  B3 ----> C7
  
  B4 ----> C2
  B4 ----> C1
  B4 ----> C6([:core:resource]):::coreModule
  B4 ----> C7
  B4 ----> C8
  
  C5 ~~~ C3([:core:database]):::coreModule
  C5 ~~~ C4([:core:datastore]):::coreModule
```

## スクリーンショット
<details>
<summary>主要画面</summary>

|説明|ダークモード|ライトモード|
|---|---|---|
|色作成（ピッカー）|<img src="https://user-images.githubusercontent.com/60963155/227682827-025fdba3-6367-41a8-8086-5bb7e9adafed.png" width="350">|<img src="https://user-images.githubusercontent.com/60963155/227682813-0b420716-5b43-4b34-b323-bf288548e796.png" width="350">|
|色作成（シークバー）|<img src="https://user-images.githubusercontent.com/60963155/227682846-767f9d0e-9b5d-4248-a13e-99875b079bd9.png" width="350">|<img src="https://user-images.githubusercontent.com/60963155/227682852-65337cbb-4d50-4032-9b16-855638571e8f.png" width="350">|
|色作成（テキスト）|<img src="https://user-images.githubusercontent.com/60963155/227682845-c5e85817-64fa-4e6d-8070-6b296e854c41.png" width="350">|<img src="https://user-images.githubusercontent.com/60963155/227682851-b8155bf6-3617-45f7-92b7-19486eee7f22.png" width="350">|
|色作成（写真）|<img src="https://user-images.githubusercontent.com/60963155/227682828-80649ffe-f1c4-4e99-9160-0bee9b367df1.png" width="350">|<img src="https://user-images.githubusercontent.com/60963155/227682817-11f930dd-5202-43bb-b083-ba83ebc03e67.png" width="350">|
|色作成（混色）|<img src="https://user-images.githubusercontent.com/60963155/227682829-5b89f126-618d-448a-b13d-bb95542cd33c.png" width="350">|<img src="https://user-images.githubusercontent.com/60963155/227682819-d63d8e6c-9c65-4104-b04a-83a643cc7bd5.png" width="350">|
|色作成（ランダム）|<img src="https://user-images.githubusercontent.com/60963155/227682830-5868530b-a770-4cdc-8aa0-3b09387e40e3.png" width="350">|<img src="https://user-images.githubusercontent.com/60963155/227682821-61903dac-c688-4dcf-a2c3-9cade62612ee.png" width="350">|
|データ一覧|<img src="https://user-images.githubusercontent.com/60963155/227682832-c45325b3-063c-4893-b660-9ac58ef26181.png" width="350">|<img src="https://user-images.githubusercontent.com/60963155/227682823-8dc1ec40-a760-4367-b36a-342123aff4f8.png" width="350">|
|分割色作成|<img src="https://user-images.githubusercontent.com/60963155/227682844-6d17a84d-658b-46de-927c-8b23df1e3f1f.png" width="350">|<img src="https://user-images.githubusercontent.com/60963155/227682850-75ecf225-3783-4fc0-9ed4-3b4198036ba0.png" width="350">|
|グラデーション作成|<img src="https://user-images.githubusercontent.com/60963155/227682834-fa71eec7-53ee-4db4-87cb-8413bf345fa5.png" width="350">|<img src="https://user-images.githubusercontent.com/60963155/227682826-04cf0473-4b47-4f54-b42a-fc3454823d1b.png" width="350">|
|設定|<img src="https://user-images.githubusercontent.com/60963155/227685726-dedc7b16-27e2-4d18-aeb2-dd01c6bea6da.png" width="350">|<img src="https://user-images.githubusercontent.com/60963155/227682847-28f8dc7d-bee7-46d8-a966-226f19ad45c4.png" width="350">|
</details>

<details>
<summary>その他画面</summary>

|説明|画面|
|---|---|
|インフォ|<img src="https://user-images.githubusercontent.com/60963155/227682840-ab88422a-f474-4dde-985b-8fd149a89083.png" width="350">|
|色選択|<img src="https://user-images.githubusercontent.com/60963155/227682842-48b6323e-2f02-4318-8031-7ca1070d3b6c.png" width="350">|
|グラデーション|<img src="https://user-images.githubusercontent.com/60963155/227682835-e8d0e7ef-0ac8-41f8-88c9-00ccb1103fc0.png" width="350">|
|単色|<img src="https://user-images.githubusercontent.com/60963155/227682838-2504dfc0-e109-4fb9-97f4-de2946c8f857.png" width="350">|
|カテゴリー詳細|<img src="https://user-images.githubusercontent.com/60963155/227682839-09f8beb6-7d1c-479e-9d2a-933cc68fe779.png" width="350">|
</details>

## アプリ内のデフォルトカラーデータについて
#### 以下からデータを設定しました
- JIS慣用色名 『フリー百科事典ウィキペディア（Wikipedia）』  
最終更新: 2021年3月24日 (水) 06:53(UTC)  
URL: https://ja.wikipedia.org/wiki/JIS慣用色名  

- JIS安全色 『フリー百科事典ウィキペディア（Wikipedia）』  
最終更新: 2021年2月5日 (金) 00:19(UTC)  
URL: https://ja.wikipedia.org/wiki/JIS安全色  

- ウェブカラー 『フリー百科事典ウィキペディア（Wikipedia）』   
最終更新: 2020年11月20日 (金) 08:15(UTC)  
URL: https://ja.wikipedia.org/wiki/ウェブカラー  

- X11の色名称 『フリー百科事典ウィキペディア（Wikipedia）』  
最終更新: 2021年5月31日 (月) 00:18(UTC)  
URL: https://ja.wikipedia.org/wiki/X11の色名称  

- コピック  
最終更新: 2021年5月10日  
URL: https://copic.jp/about/color-system/  

