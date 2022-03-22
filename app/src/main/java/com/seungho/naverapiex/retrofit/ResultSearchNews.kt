package com.seungho.naverapiex.retrofit

data class ResultSearchNews(
    var lastBuildDate: String = "", //검색 결과를 생성한 시간
    var total: Int = 0, //검색 결과 총 개수
    var start: Int = 0, //검색 결과 문서 중, 문서의 시작점
    var display : Int = 0, //검색된 검색 결과의 개수
    var items: List<Items>
)

data class Items(
    var title: String = "", //제목
    var originalLink: String = "", //언론사 하이퍼 텍스트
    var link: String = "", //네이버 하이퍼 텍스트
    var description: String = "", // 검색 결과 문서의 내용을 요약한 패시지 정보
    var pubDate: String = "" // 겸색 결과 문서가 네이버에 제공된 시간

)
