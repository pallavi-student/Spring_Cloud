import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuizInterface quizInterface;

    // Circuit Breaker applied here
    @CircuitBreaker(name = "quizServiceBreaker", fallbackMethod = "createQuizFallback")
    public ResponseEntity<String> createQuiz(String category, int numQuestion, String title) {
        if (numQuestion <= 0) {
            return new ResponseEntity<>("Number of questions must be greater than 0", HttpStatus.BAD_REQUEST);
        }
        List<Integer> questions = quizInterface.getQuestionForQuiz(category, numQuestion).getBody();
        if (questions == null || questions.isEmpty()) {
            return new ResponseEntity<>("No questions found for the given category", HttpStatus.BAD_REQUEST);
        }
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("Created", HttpStatus.OK);
    }

    // Fallback for createQuiz
    public ResponseEntity<String> createQuizFallback(String category, int numQuestion, String title, Exception e) {
        System.out.println("Fallback executed for createQuiz: " + e.getMessage());
        return new ResponseEntity<>("Quiz Service is down, try later.", HttpStatus.SERVICE_UNAVAILABLE);
    }

    @CircuitBreaker(name = "quizServiceBreaker", fallbackMethod = "getQuizQuestionsFallback")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Quiz quiz = quizDao.findById(id).get();
        List<Integer> questionIds = quiz.getQuestionIds();
        ResponseEntity<List<QuestionWrapper>> questions = quizInterface.getQuestionsFromId(questionIds);
        return questions;
    }

    // Fallback
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestionsFallback(Integer id, Exception e) {
        System.out.println("Fallback executed for getQuizQuestions: " + e.getMessage());
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @CircuitBreaker(name = "quizServiceBreaker", fallbackMethod = "calculateResultFallback")
    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        ResponseEntity<Integer> score = quizInterface.getScore(responses);
        return score;
    }

    // Fallback
    public ResponseEntity<Integer> calculateResultFallback(Integer id, List<Response> responses, Exception e) {
        System.out.println("Fallback executed for calculateResult: " + e.getMessage());
        return new ResponseEntity<>(-1, HttpStatus.SERVICE_UNAVAILABLE); // -1 means failure
    }
}
