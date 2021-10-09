package com.example.marimo_back.service;

import com.example.marimo_back.Dto.RecordResponseDto;
import com.example.marimo_back.domain.*;
import com.example.marimo_back.repository.RecordRepository;
import com.example.marimo_back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class RecordService {

    private final UserRepository userRepository;
    private final RecordRepository recordRepository;

    public RecordResponseDto getUserAchievement(Long userId) {
        Users user = userRepository.findById(userId);

        double successCount = recordRepository.getUserSuccessCount(user);
        double failCount = recordRepository.getUserFailCount(user);
        Long achievementRate = Math.round((successCount / (successCount + failCount)) * 100);

        List<String> mostSuccess = recordRepository.findMostSuccessWord(user);
        List<String> mostFail = recordRepository.findMostFailWord(user);

        Long gameJoinCount = recordRepository.getGameJoinCount(user);

        Integer mostSuccessWordCount = recordRepository.findMostSuccessWordCount(user);

        //한국어의 치경마찰음 /ㅅ, ㅆ/ 음소는 정상 아동의 말소리 발달 과정에서 가장 늦게 습득되는 말소리에 속하며, 긴 시간 동안 점진적인 과정을 통하여 발달되는 소리이다.
        // 또한 조음 오류를 보이는 아동에게서 가장 자주 오조음되는 말소리로 알려져 있기도 하다.
        // 한국어 치경마찰음 /ㅅ, ㅆ/는 생략 및 /ㅎ/로부터 시작하여 치경파열음 /ㄷ, ㄸ, ㅌ/, 파찰음 /ㅈ, ㅉ, ㅊ/, 구개음화된 마찰음 /ɕ, ɕ*/, 그리고 치간음화된 마찰음 /ɵ, ɵ*/의 과정을 거쳐 발달하는 것으로 나타났다

        // ㅏ, ㅓ, ㅗ, ㅜ, ㅡ, ㅣ) ㄱ, ㄴ, ㄷ, ㄹ, ㅁ, ㅂ, ㅅ, ㅈ 은 6세 이전에 발음을 잘한다

        //종성 연구개음 /ㄱ, ㅇ/, 초성 유음 /ㄹ/, 치조마찰음 /ㅅ, ㅆ/의 습득 연령이 늦게 나타났다 --> 일반적 발달요소

        String[] mostFrequentWeaknessVowels = {"ㄱ","ㅇ","ㄹ","ㅅ","ㅆ"};
        String[] doubleVowels = {"ㄲ","ㄸ","ㅃ","ㅆ","ㅉ"}; //된소리
        String[] normalVowels = {"ㄱ", "ㄷ", "ㅂ", "ㅈ", "ㅅ"}; //예사소리
        String[] roughVowels = {"ㅋ", "ㅌ", "ㅍ", "ㅊ"}; //거센소리
        String[] CHO = {"ㄱ", "ㄲ", "ㄴ", "ㄷ", "ㄸ", "ㄹ", "ㅁ", "ㅂ", "ㅃ", "ㅅ", "ㅆ", "ㅇ", "ㅈ", "ㅉ", "ㅊ", "ㅋ", "ㅌ", "ㅍ", "ㅎ"};
        String[] JOONG = {"ㅏ", "ㅐ", "ㅑ", "ㅒ", "ㅓ", "ㅔ", "ㅕ", "ㅖ", "ㅗ", "ㅘ", "ㅙ", "ㅚ", "ㅛ", "ㅜ", "ㅝ", "ㅞ", "ㅟ", "ㅠ", "ㅡ", "ㅢ", "ㅣ"};
        String[] JONG = {"", "ㄱ", "ㄲ", "ㄳ", "ㄴ", "ㄵ", "ㄶ", "ㄷ", "ㄹ", "ㄺ", "ㄻ", "ㄼ", "ㄽ", "ㄾ", "ㄿ", "ㅀ", "ㅁ", "ㅂ", "ㅄ", "ㅅ", "ㅆ", "ㅇ", "ㅈ", "ㅊ", "ㅋ", "ㅌ", "ㅍ", "ㅎ"};



        Map<String, Integer> sorted_reasons = userFailAnalysis(userId);
        //틀리는 요소를 모두가져온다. 가장 많이 틀리는 것들을 가져온다. 모음 자음 각각.

        int i=0;
        sorted_reasons.remove(null);
        final int[] allStartMiddleEnd = {0,0,0};

        Map<String, List<Integer>> reason_feedbacksort = new LinkedHashMap<>();
        final int[] doublevowelscount = {0};
        final int[] normalvowelscount = {0};
        final int[] roughvowelscount = {0};

        final  int[] freqeuntcount ={0};
        sorted_reasons.forEach((s, integer) -> {

            if(sorted_reasons.size()-i<10 && !s.equals(null)) {

                if(Arrays.asList(doubleVowels).contains(s)) doublevowelscount[0]++;
                if(Arrays.asList(normalVowels).contains(s)) normalvowelscount[0]++;
                if(Arrays.asList(roughVowels).contains(s)) roughvowelscount[0]++;

                List<Integer> count = recordRepository.getVowelFeedback(user, s);
                int start =0; start = Collections.frequency(count, 1); allStartMiddleEnd[0]=allStartMiddleEnd[0]+start;
                int middle =0; middle = Collections.frequency(count,2); allStartMiddleEnd[1]=allStartMiddleEnd[1]+middle;
                int end = 0; end =  Collections.frequency(count,3); allStartMiddleEnd[2]=allStartMiddleEnd[2]+end;
                List<Integer> t = new ArrayList<>();
                t.add(integer);
                t.add(start);
                t.add(middle);
                t.add(end);
                if(Arrays.asList(mostFrequentWeaknessVowels).contains(s)) freqeuntcount[0]++;

                reason_feedbacksort.put(s,t);
            }
        });

        // 상위 10가지중에서 모음 자음의 비율을 확인하여 둘중 어떤게 더 약한지 확인한다.
        // 초중종성 중 어떤게 가장 약한지 ->1
        // 자음의 경우 초성인지 종성인지 (각1,3)을 분석한다.
        final String[] weaknessStartMiddleEnd = {(allStartMiddleEnd[0] > allStartMiddleEnd[1]) && (allStartMiddleEnd[0] > allStartMiddleEnd[2]) ? "중성과 종성" : (allStartMiddleEnd[2] > allStartMiddleEnd[1] ? "초성과 모음" : "초성과 중성")};
        weaknessStartMiddleEnd[0] = weaknessStartMiddleEnd[0] +"에 능숙합니다. 아직 ";
        System.out.println(weaknessStartMiddleEnd[0] );
        weaknessStartMiddleEnd[0] = weaknessStartMiddleEnd[0] + ((doublevowelscount[0] > normalvowelscount[0]) && (doublevowelscount[0] > roughvowelscount[0]) ? "된소리" : (roughvowelscount[0] > normalvowelscount[0] ? "거센소리" : "예사소리"));
        weaknessStartMiddleEnd[0] = weaknessStartMiddleEnd[0] +"의 발음에 어려움을 겪습니다.";

        // 일반적 발달요소에 포함되지 않는 요소일 경우
        if(freqeuntcount[0]>0) weaknessStartMiddleEnd[0]= weaknessStartMiddleEnd[0]+"하지만 어려워하는 자음들은 적절한 발달과정이니 걱정마세요!";

        Map<String, Integer> mostSuccessWordGame = new LinkedHashMap<>();
        Map<String, Integer> mostSuccessWordTale = new LinkedHashMap<>();
        Map<String, Integer> mostSuccessWordExplore = new LinkedHashMap<>();
        final int[] count = {0};
        boolean[] get = {false,false,false};//game, tale, explore
        List<SuccessWord> successwords = recordRepository.successWords5(user);
        successwords.forEach(w->{
            count[0]++;
            System.out.println(w.getWord()+w.getNum()+w.getCategory());
            if(w.getCategory().equals(Category.GAME)&&!get[0]){
                mostSuccessWordGame.put(w.getWord(),w.getNum());
                get[0]=true;
            }
            if(w.getCategory().equals(Category.TALE)&&!get[1]){
                mostSuccessWordTale.put(w.getWord(),w.getNum());
                get[1]=true;
            }
            if(w.getCategory().equals(Category.EXPLORE)&&!get[2]){
                mostSuccessWordExplore.put(w.getWord(),w.getNum());
                get[2]=true;
            }
        });

        String[] taleName = {"호랑이의 생일잔치"};
        List<Tale> tales = recordRepository.tales(user, taleName[0]);
        int[] taleplaynum = {0};
        taleplaynum[0]=tales.size();

        List<SuccessWord> taleBestWords = recordRepository.categoryBestSuccessWord(user, Category.TALE);
        String taleBestWord="";
        if(taleBestWords.size()!=0) taleBestWord=taleBestWords.get(0).getWord();


        List<SuccessWord> gameBestWord = recordRepository.categoryBestSuccessWord(user,Category.GAME);


        return RecordResponseDto.builder()
                .nickName(user.getNickname())
                .registerDate(user.getRegidate())
                .achievementRate(achievementRate)
                .mostSuccess(mostSuccess)
                .mostFail(mostFail)
                .gameJoinNum(gameJoinCount)
                .successCount(mostSuccessWordCount)
                .analysis(weaknessStartMiddleEnd[0])
                .successwordInGame(mostSuccessWordGame)
                .successwordInTale(mostSuccessWordTale)
                .successwordInExplore(mostSuccessWordExplore)
                .talePlayCount(taleplaynum[0])
                .gamePlayCount(recordRepository.gamePlayCount(user).size())
                .gameBestWord(gameBestWord.get(0).getWord())
                .taleBestWord(taleBestWord)
                .build();
    }

    public Map<String, Integer> userFailAnalysis(Long userId){

        Users user = userRepository.findById(userId);

        List<String> fail_reasons = recordRepository.getAllFailRisk(user);
        Map<String, Integer> all_reason_count = new LinkedHashMap<>();

        for(int i=0; i<fail_reasons.size(); i++){
            String s = fail_reasons.get(i);
            if(all_reason_count.containsKey(s)){
                int t = all_reason_count.get(s)+1;
                all_reason_count.remove(s);
                all_reason_count.put(s, t);
            }
            else{
                all_reason_count.put(s,1);
            }
        }
        final int[] t = {0};
        Map<String, Integer> sorted_reasons =sortMapByValue( all_reason_count);

        for (Map.Entry<String, Integer> entry : sorted_reasons.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", "
                    + "Value: " + entry.getValue());
        }
        return sorted_reasons;

    }

    public static LinkedHashMap<String, Integer> sortMapByValue(Map<String, Integer> map) {
        List<Map.Entry<String, Integer>> entries = new LinkedList<>(map.entrySet());
        Collections.sort(entries, (o1, o2) -> o1.getValue().compareTo(o2.getValue()));

        LinkedHashMap<String, Integer> result = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : entries) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

}
