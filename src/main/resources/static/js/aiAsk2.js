// node --version # Should be >= 18
// npm install @google/generative-ai

const {
    GoogleGenerativeAI,
    HarmCategory,
    HarmBlockThreshold,
} = require("@google/generative-ai");

const MODEL_NAME = "gemini-1.0-pro-001";
const API_KEY = "AIzaSyDjjJn_37Y6NuthLBX-VSXWQHqNpUFGYyw"; //개인 API 사용필요.

async function runChat(userInput) {
    const genAI = new GoogleGenerativeAI(API_KEY);
    const model = genAI.getGenerativeModel({ model: MODEL_NAME });

    const generationConfig = {
        temperature: 0,
        topK: 1,
        topP: 1,
        maxOutputTokens: 2048,
    };

    const safetySettings = [
        {
            category: HarmCategory.HARM_CATEGORY_HARASSMENT,
            threshold: HarmBlockThreshold.BLOCK_MEDIUM_AND_ABOVE,
        },
        {
            category: HarmCategory.HARM_CATEGORY_HATE_SPEECH,
            threshold: HarmBlockThreshold.BLOCK_MEDIUM_AND_ABOVE,
        },
        {
            category: HarmCategory.HARM_CATEGORY_SEXUALLY_EXPLICIT,
            threshold: HarmBlockThreshold.BLOCK_MEDIUM_AND_ABOVE,
        },
        {
            category: HarmCategory.HARM_CATEGORY_DANGEROUS_CONTENT,
            threshold: HarmBlockThreshold.BLOCK_MEDIUM_AND_ABOVE,
        },
    ];
    const parts = [
        {text: "input: (머리 증상 : 두통, 빈혈) 증상일 때 어느 과를 가야할까?"},
        {text: "output: 신경과\n"},
        {text: "input: (어깨 증상 : 발열, 가동범위제한) 증상일 때 어느 과를 가야할까?"},
        {text: "output: 정형외과\n"},
        {text: "input: (가슴 증상 : 내부통증, 숨쉬기 어려움) 증상일 때 어느 과를 가야할까?"},
        {text: "output: 흉부외과\n"},
        {text: `input: ${userInput}`},
        {text: "output: "},
    ];

    const result = await model.generateContent({
        contents: [{ role: "user", parts }],
        generationConfig,
        safetySettings,
    });
    const response = result.response;
    console.log(response.text());
}
const userInput = process.argv[2];
runChat(userInput);