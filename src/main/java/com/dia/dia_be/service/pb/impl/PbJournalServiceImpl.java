package com.dia.dia_be.service.pb.impl;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dia.dia_be.domain.Journal;
import com.dia.dia_be.domain.JournalProduct;
import com.dia.dia_be.domain.Product;
import com.dia.dia_be.domain.Script;
import com.dia.dia_be.domain.Speaker;
import com.dia.dia_be.dto.pb.journalDTO.RequestJournalDTO;
import com.dia.dia_be.dto.pb.journalDTO.ResponseJournalDTO;
import com.dia.dia_be.dto.pb.journalDTO.ScriptListResponseDTO;
import com.dia.dia_be.dto.pb.journalDTO.ScriptResponseDTO;
import com.dia.dia_be.exception.GlobalException;
import com.dia.dia_be.exception.PbErrorCode;
import com.dia.dia_be.global.clovaSpeech.ClovaSpeechService;
import com.dia.dia_be.repository.ConsultingRepository;
import com.dia.dia_be.repository.JournalProductRepository;
import com.dia.dia_be.repository.JournalRepository;
import com.dia.dia_be.repository.ProductRepository;
import com.dia.dia_be.repository.ScriptRepository;
import com.dia.dia_be.service.pb.intf.PbJournalService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PbJournalServiceImpl implements PbJournalService {

	private final JournalRepository journalRepository;
	private final ClovaSpeechService clovaSpeechService;
	private final ScriptRepository scriptRepository;
	private final ConsultingRepository consultingRepository;
	private final ProductRepository productRepository;
	private final JournalProductRepository journalProductRepository;
	public PbJournalServiceImpl(JournalRepository journalRepository, ClovaSpeechService clovaSpeechService, ScriptRepository scriptRepository, ConsultingRepository consultingRepository, JournalProductRepository journalProductRepository, ProductRepository productRepository,
		JournalProductRepository journalProductRepository1) {
		this.journalRepository = journalRepository;
		this.clovaSpeechService = clovaSpeechService;
		this.scriptRepository = scriptRepository;
		this.consultingRepository = consultingRepository;
		this.productRepository = productRepository;
		this.journalProductRepository = journalProductRepository1;
	}

	@Override
	public List<ResponseJournalDTO> getJournals() {
		return journalRepository.findAll().stream().map(ResponseJournalDTO::from).toList();

	}

	@Override
	public ResponseJournalDTO getJournal(Long id) {
		return ResponseJournalDTO.from(
			journalRepository.findById(id).orElseThrow(() -> new GlobalException(PbErrorCode.JOURNAL_NOT_FOUND)));
	}

	@Override
	public ScriptListResponseDTO createScriptsAndKeyword(Long journalId, String filePath) {
		Journal journal = journalRepository.findById(journalId).get();
		// String sttResult = clovaSpeechService.stt(filePath);
		String sttResult = "{\n"
			+ "  \"result\": \"COMPLETED\",\n"
			+ "  \"message\": \"Succeeded\",\n"
			+ "  \"token\": \"ec6c147e76bb43739cc87a05460a2dea\",\n"
			+ "  \"version\": \"ncp_v2_v2.3.8-22e1a5c-20241121_240905-28192935-dirty_v4.2.12_ko_firedepartment_20240624_\",\n"
			+ "  \"params\": {\n"
			+ "    \"service\": \"ncp\",\n"
			+ "    \"domain\": \"general\",\n"
			+ "    \"lang\": \"ko\",\n"
			+ "    \"completion\": \"sync\",\n"
			+ "    \"callback\": \"\",\n"
			+ "    \"diarization\": {\n"
			+ "      \"enable\": true,\n"
			+ "      \"speakerCountMin\": 2,\n"
			+ "      \"speakerCountMax\": 2\n"
			+ "    },\n"
			+ "    \"sed\": { \"enable\": false },\n"
			+ "    \"boostings\": [],\n"
			+ "    \"forbiddens\": \"\",\n"
			+ "    \"wordAlignment\": true,\n"
			+ "    \"fullText\": true,\n"
			+ "    \"noiseFiltering\": true,\n"
			+ "    \"resultToObs\": false,\n"
			+ "    \"priority\": 0,\n"
			+ "    \"userdata\": {\n"
			+ "      \"_ncp_DomainCode\": \"dia\",\n"
			+ "      \"_ncp_DomainId\": 9829,\n"
			+ "      \"_ncp_TaskId\": 25240153,\n"
			+ "      \"_ncp_TraceId\": \"87754d7e2d934f3a8ddfcfda211a021e\"\n"
			+ "    }\n"
			+ "  },\n"
			+ "  \"progress\": 100,\n"
			+ "  \"keywords\": {},\n"
			+ "  \"segments\": [\n"
			+ "    {\n"
			+ "      \"start\": 0,\n"
			+ "      \"end\": 879,\n"
			+ "      \"text\": \"내가 아까 말했는\",\n"
			+ "      \"confidence\": 0.8442,\n"
			+ "      \"diarization\": { \"label\": \"1\" },\n"
			+ "      \"speaker\": { \"label\": \"1\", \"name\": \"A\", \"edited\": false },\n"
			+ "      \"words\": [\n"
			+ "        [50, 194, \"내가\"],\n"
			+ "        [194, 360, \"아까\"],\n"
			+ "        [450, 860, \"말했는\"]\n"
			+ "      ],\n"
			+ "      \"textEdited\": \"내가 아까 말했는\"\n"
			+ "    },\n"
			+ "    {\n"
			+ "      \"start\": 879,\n"
			+ "      \"end\": 3677,\n"
			+ "      \"text\": \"안녕하세요. 저는 유진이라고 합니다. 앞으로 잘 부탁드립니다.\",\n"
			+ "      \"confidence\": 0.9889,\n"
			+ "      \"diarization\": { \"label\": \"2\" },\n"
			+ "      \"speaker\": { \"label\": \"2\", \"name\": \"B\", \"edited\": false },\n"
			+ "      \"words\": [\n"
			+ "        [889, 1339, \"안녕하세요.\"],\n"
			+ "        [1409, 1619, \"저는\"],\n"
			+ "        [1629, 2179, \"유진이라고\"],\n"
			+ "        [2179, 2439, \"합니다.\"],\n"
			+ "        [2549, 2859, \"앞으로\"],\n"
			+ "        [2909, 3059, \"잘\"],\n"
			+ "        [3069, 3677, \"부탁드립니다.\"]\n"
			+ "      ],\n"
			+ "      \"textEdited\": \"안녕하세요. 저는 유진이라고 합니다. 앞으로 잘 부탁드립니다.\"\n"
			+ "    },\n"
			+ "    {\n"
			+ "      \"start\": 3677,\n"
			+ "      \"end\": 8154,\n"
			+ "      \"text\": \"안녕하세요. 저는 민지예요. 혹시 어디에서 오셨어요?\",\n"
			+ "      \"confidence\": 0.9734,\n"
			+ "      \"diarization\": { \"label\": \"1\" },\n"
			+ "      \"speaker\": { \"label\": \"1\", \"name\": \"A\", \"edited\": false },\n"
			+ "      \"words\": [\n"
			+ "        [4287, 4857, \"안녕하세요.\"],\n"
			+ "        [5067, 5317, \"저는\"],\n"
			+ "        [5507, 6017, \"민지예요.\"],\n"
			+ "        [6407, 6677, \"혹시\"],\n"
			+ "        [6727, 7217, \"어디에서\"],\n"
			+ "        [7307, 7777, \"오셨어요?\"]\n"
			+ "      ],\n"
			+ "      \"textEdited\": \"안녕하세요. 저는 민지예요. 혹시 어디에서 오셨어요?\"\n"
			+ "    },\n"
			+ "    {\n"
			+ "      \"start\": 8154,\n"
			+ "      \"end\": 15580,\n"
			+ "      \"text\": \"저는 이전에는 디자인 팀에서 일했어요. 이번에 개발 쪽으로 새로 도전하게 됐습니다.\",\n"
			+ "      \"confidence\": 0.9715,\n"
			+ "      \"diarization\": { \"label\": \"2\" },\n"
			+ "      \"speaker\": { \"label\": \"2\", \"name\": \"B\", \"edited\": false },\n"
			+ "      \"words\": [\n"
			+ "        [8444, 8694, \"저는\"],\n"
			+ "        [8844, 9414, \"이전에는\"],\n"
			+ "        [9904, 10254, \"디자인\"],\n"
			+ "        [10304, 10734, \"팀에서\"],\n"
			+ "        [10764, 11214, \"일했어요.\"],\n"
			+ "        [11864, 12174, \"이번에\"],\n"
			+ "        [12464, 12694, \"개발\"],\n"
			+ "        [12744, 13074, \"쪽으로\"],\n"
			+ "        [13184, 13394, \"새로\"],\n"
			+ "        [13664, 14134, \"도전하게\"],\n"
			+ "        [14184, 14694, \"됐습니다.\"]\n"
			+ "      ],\n"
			+ "      \"textEdited\": \"저는 이전에는 디자인 팀에서 일했어요. 이번에 개발 쪽으로 새로 도전하게 됐습니다.\"\n"
			+ "    },\n"
			+ "    {\n"
			+ "      \"start\": 15580,\n"
			+ "      \"end\": 18623,\n"
			+ "      \"text\": \"디자인에서 개발로요. 대단하시네요.\",\n"
			+ "      \"confidence\": 0.9787,\n"
			+ "      \"diarization\": { \"label\": \"1\" },\n"
			+ "      \"speaker\": { \"label\": \"1\", \"name\": \"A\", \"edited\": false },\n"
			+ "      \"words\": [\n"
			+ "        [16170, 16800, \"디자인에서\"],\n"
			+ "        [16870, 17380, \"개발로요.\"],\n"
			+ "        [17850, 18620, \"대단하시네요.\"]\n"
			+ "      ],\n"
			+ "      \"textEdited\": \"디자인에서 개발로요. 대단하시네요.\"\n"
			+ "    },\n"
			+ "    {\n"
			+ "      \"start\": 18623,\n"
			+ "      \"end\": 21905,\n"
			+ "      \"text\": \"맞아요. 멀티 플레이어인가 보다.\",\n"
			+ "      \"confidence\": 0.8701,\n"
			+ "      \"diarization\": { \"label\": \"2\" },\n"
			+ "      \"speaker\": { \"label\": \"2\", \"name\": \"B\", \"edited\": false },\n"
			+ "      \"words\": [\n"
			+ "        [19473, 19843, \"맞아요.\"],\n"
			+ "        [20093, 20363, \"멀티\"],\n"
			+ "        [20533, 21210, \"플레이어인가\"],\n"
			+ "        [21210, 21403, \"보다.\"]\n"
			+ "      ],\n"
			+ "      \"textEdited\": \"맞아요. 멀티 플레이어인가 보다.\"\n"
			+ "    },\n"
			+ "    {\n"
			+ "      \"start\": 21905,\n"
			+ "      \"end\": 29080,\n"
			+ "      \"text\": \"우리 팀에는 다양한 분야를 아우르는 분이 많아서 그건 미리 각오해 두시고요.\",\n"
			+ "      \"confidence\": 0.9959,\n"
			+ "      \"diarization\": { \"label\": \"1\" },\n"
			+ "      \"speaker\": { \"label\": \"1\", \"name\": \"A\", \"edited\": false },\n"
			+ "      \"words\": [\n"
			+ "        [22295, 22485, \"우리\"],\n"
			+ "        [22515, 22965, \"팀에는\"],\n"
			+ "        [23055, 23405, \"다양한\"],\n"
			+ "        [23535, 23905, \"분야를\"],\n"
			+ "        [23955, 24405, \"아우르는\"],\n"
			+ "        [24435, 24665, \"분이\"],\n"
			+ "        [24675, 25085, \"많아서\"],\n"
			+ "        [26735, 26945, \"그건\"],\n"
			+ "        [27015, 27225, \"미리\"],\n"
			+ "        [27275, 27645, \"각오해\"],\n"
			+ "        [27645, 28085, \"두시고요.\"]\n"
			+ "      ],\n"
			+ "      \"textEdited\": \"우리 팀에는 다양한 분야를 아우르는 분이 많아서 그건 미리 각오해 두시고요.\"\n"
			+ "    },\n"
			+ "    {\n"
			+ "      \"start\": 29080,\n"
			+ "      \"end\": 30980,\n"
			+ "      \"text\": \"저도 적당히 해요.\",\n"
			+ "      \"confidence\": 0.8869,\n"
			+ "      \"diarization\": { \"label\": \"2\" },\n"
			+ "      \"speaker\": { \"label\": \"2\", \"name\": \"B\", \"edited\": false },\n"
			+ "      \"words\": [\n"
			+ "        [29750, 29960, \"저도\"],\n"
			+ "        [29990, 30380, \"적당히\"],\n"
			+ "        [30390, 30600, \"해요.\"]\n"
			+ "      ],\n"
			+ "      \"textEdited\": \"저도 적당히 해요.\"\n"
			+ "    }\n"
			+ "  ],\n"
			+ "  \"text\": \"내가 아까 말했는 안녕하세요. 저는 유진이라고 합니다. 앞으로 잘 부탁드립니다. 안녕하세요. 저는 민지예요. 혹시 어디에서 오셨어요? 저는 이전에는 디자인 팀에서 일했어요. 이번에 개발 쪽으로 새로 도전하게 됐습니다. 디자인에서 개발로요. 대단하시네요. 맞아요. 멀티 플레이어인가 보다. 우리 팀에는 다양한 분야를 아우르는 분이 많아서 그건 미리 각오해 두시고요. 저도 적당히 해요.\",\n"
			+ "  \"confidence\": 0.96199644,\n"
			+ "  \"speakers\": [\n"
			+ "    { \"label\": \"1\", \"name\": \"A\", \"edited\": false },\n"
			+ "    { \"label\": \"2\", \"name\": \"B\", \"edited\": false }\n"
			+ "  ],\n"
			+ "  \"events\": [],\n"
			+ "  \"eventTypes\": []\n"
			+ "}\n";
		System.out.println("여기"+sttResult);
		List<ScriptResponseDTO> scriptResponseDTOList = new LinkedList<>();
		try {
			// ObjectMapper 객체 생성
			ObjectMapper objectMapper = new ObjectMapper();

			// JSON 문자열을 JsonNode로 파싱
			JsonNode rootNode = objectMapper.readTree(sttResult);
			JsonNode segmentsNode = rootNode.get("segments");

			// segments 배열 순회
			if (segmentsNode.isArray()) {
				int sequence = 1; // 순서값 초기화

				for (JsonNode segment : segmentsNode) {
					// 필요한 값 추출
					String textEdited = segment.get("textEdited").asText();
					int label = Integer.parseInt(segment.get("speaker").get("label").asText());

					// 새로운 객체 노드 생성
					System.out.println("here"+label);
					Script beforeScript = Script.create(journal,sequence++,
						label == 1 ? Speaker.VIP:Speaker.PB,textEdited);
					Script addScript = scriptRepository.save(beforeScript);
					scriptResponseDTOList.add(ScriptResponseDTO.from(addScript));
				}
			}

			// 최종 결과 출력
			System.out.println("Result JSON Array:");
			System.out.println(Arrays.toString(scriptResponseDTOList.toArray()));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return ScriptListResponseDTO.of(scriptResponseDTOList);
	}

	@Override
	@Transactional
	public void addJournal(RequestJournalDTO body) {
		consultingRepository.updateTitleAndCategory(body.getConsultingId(),
			body.getConsultingTitle(), body.getCategoryId());

		journalRepository.updateContentsById(body.getConsultingId(), body.getJournalContents());
		List<Product> products = productRepository.findAllById(body.getRecommendedProductsKeys());
		Journal journal = journalRepository.findById(body.getConsultingId())
			.orElseThrow(() -> new GlobalException(PbErrorCode.JOURNAL_NOT_FOUND));
		for(Product product : products){
			JournalProduct journalProduct = JournalProduct.create(product, journal);
			journalProductRepository.save(journalProduct);
		}
	}

	@Override
	@Transactional
	public void addJournalAndChangeStatusComplete(RequestJournalDTO body) {
		addJournal(body);
		journalRepository.updateCompleteStatusById(body.getConsultingId());
	}

}
