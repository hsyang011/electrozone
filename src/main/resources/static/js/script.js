function searchItem() {
    const searchTerm = document.getElementById('search').value.trim();
    if (searchTerm) {
        alert(`"${searchTerm}"에 대한 검색 결과를 보여줍니다.`);
    } else {
        alert("검색어를 입력해주세요.");
        document.getElementById('search').focus(); // 검색창에 초점
    }
}

