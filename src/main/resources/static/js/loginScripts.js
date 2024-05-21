window.onload = function() {
    const btn = document.querySelector('.btn__primary');
    const originalText = btn.innerText;
    const newText = "Let's Go"; // 마우스를 올렸을 때 보여질 새로운 텍스트

    btn.addEventListener('mouseover', function() {
        btn.innerText = newText;
    });

    btn.addEventListener('mouseout', function() {
        btn.innerText = originalText;
    });
}