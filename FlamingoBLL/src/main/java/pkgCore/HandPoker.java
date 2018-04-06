package pkgCore;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import pkgConstants.*;
import pkgEnum.eCardNo;
import pkgEnum.eHandStrength;
import pkgEnum.eRank;
import pkgEnum.eSuit;

public class HandPoker extends Hand {

	private ArrayList<CardRankCount> CRC = null;

	public HandPoker() {
		this.setHS(new HandScorePoker());
	}

	protected ArrayList<CardRankCount> getCRC() {
		return CRC;
	}

	@Override
	public HandScore ScoreHand() {
		// TODO : Implement this method... call each of the 'is' methods (isRoyalFlush,
		// etc) until
		// one of the hands is true, then score the hand

		Collections.sort(super.getCards());
		Frequency();
		
		/*try {
			Class<?> c = Class.forName("pkgCore.HandPoker");
			
			for (eHandStrength hstr: eHandStrength.values(){
				Method meth = c.getDeclaredMethod(hstr.getEvalMethod(), null);
				Object o = meth.invoke(this, null);
				
				if ((Boolean) o) {
					break;
				}
			}
			Method methGetHandScorePoker = c.getDeclaredMethod("getHSP" , null);
			HSP = (HandScorePoker) methGetHandScorePoker.invoke(this, null);
			
		}
		catch (ClassNotFoundException f){
			
		}*/
		
		
		
		
		
		if (isRoyalFlush()) {
			return this.getHS();
		} 
		else if (isStraightFlush()) {
			return this.getHS();
		}
		else if (isFourOfAKind()) {
			return this.getHS();
		}
		else if (isFullHouse()) {
			return this.getHS();
		}
		else if (isFlush()) {
			return this.getHS();
		}
		else if (isStraight()) {
			return this.getHS();
		}
		else if (isThreeOfAKind()) {
			return this.getHS();
		}
		else if (isTwoPair()) {
			return this.getHS();
		}
		else if (isPair()) {
			return this.getHS();
		}
		else if (isHighCard()) {
			return this.getHS();
		}
		return null;
	}



	public boolean isRoyalFlush() {
		boolean bisStraight = true;
		boolean bisFlush = false;
		boolean bisRoyalStraight = false;
		
		int a = 0;
		if (super.getCards().get(0).geteRank() == eRank.ACE) {
			a = 1;
		}
		
		while (a < super.getCards().size() - 1) {
			if(super.getCards().get(a).geteRank().getiRankNbr()-1 == super.getCards().get(a+1).geteRank().getiRankNbr()) {
				bisStraight = false;
			}
			a++;
		}
		
		if((super.getCards().get(super.getCards().size()-1).geteRank() == eRank.TEN) && bisStraight == true){
			bisRoyalStraight = true;
		}
		
		int iCardCnt = super.getCards().size();
		int iSuitCnt = 0;

		for (eSuit eSuit : eSuit.values()) {
			for (Card c : super.getCards()) {
				if (eSuit == c.geteSuit()) {
					iSuitCnt++;
				}
			}
			if (iSuitCnt > 0)
				break;
		}

		if (iSuitCnt == iCardCnt)
			bisFlush = true;
		else
			bisFlush = false;
		
		if (bisFlush && bisStraight && bisRoyalStraight) {
			HandScorePoker HSP = (HandScorePoker) this.getHS();
			HSP.seteHandStrength(eHandStrength.RoyalFlush);
			int iGetCard = this.getCRC().get(0).getiCardPosition();
			HSP.setHiCard(this.getCards().get(iGetCard));
			HSP.setLoCard(null);
			HSP.setKickers(FindTheKickers(this.getCRC()));
			this.setHS(HSP);
		}
		
		return (bisStraight && bisFlush && bisRoyalStraight);
	}

	public boolean isStraightFlush() {
		boolean bisStraight = true;
		boolean bisFlush = false;
		
		int a = 0;
		if (super.getCards().get(0).geteRank() == eRank.ACE) {
			a = 1;
		}
		
		while (a < super.getCards().size() - 1) {
			if(super.getCards().get(a).geteRank().getiRankNbr()-1 == super.getCards().get(a+1).geteRank().getiRankNbr()) {
				bisStraight = false;
			}
			a++;
		}
		
		int iCardCnt = super.getCards().size();
		int iSuitCnt = 0;

		for (eSuit eSuit : eSuit.values()) {
			for (Card c : super.getCards()) {
				if (eSuit == c.geteSuit()) {
					iSuitCnt++;
				}
			}
			if (iSuitCnt > 0)
				break;
		}

		if (iSuitCnt == iCardCnt)
			bisFlush = true;
		else
			bisFlush = false;
		
		if (bisFlush && bisStraight) {
			HandScorePoker HSP = (HandScorePoker) this.getHS();
			HSP.seteHandStrength(eHandStrength.StraightFlush);
			int iGetCard = this.getCRC().get(0).getiCardPosition();
			HSP.setHiCard(this.getCards().get(iGetCard));
			HSP.setLoCard(null);
			HSP.setKickers(FindTheKickers(this.getCRC()));
			this.setHS(HSP);
		}
		return (bisFlush && bisStraight);
	}

	public boolean isFourOfAKind() {
		boolean bisFourOfAKind = false;
		if (this.getCRC().size() == 2) {
			if (this.getCRC().get(0).getiCnt() == Constants.FOUR_OF_A_KIND) {
				bisFourOfAKind = true;
				HandScorePoker HSP = (HandScorePoker) this.getHS();
				HSP.seteHandStrength(eHandStrength.FourOfAKind);
				int iGetCard = this.getCRC().get(0).getiCardPosition();
				HSP.setHiCard(this.getCards().get(iGetCard));
				HSP.setLoCard(null);
				HSP.setKickers(FindTheKickers(this.getCRC()));
				this.setHS(HSP);
			}
		}
		return bisFourOfAKind;
	}

	// TODO : Implement this method
	public boolean isFullHouse() {
		boolean bisFullHouse = false;
		
		if (this.CRC.size() == 2) {
			if ((CRC.get(0).getiCnt() == 3) && (CRC.get(1).getiCnt() == 2)) {
				bisFullHouse = true;
				HandScorePoker HSP = (HandScorePoker) this.getHS();
				HSP.seteHandStrength(eHandStrength.FullHouse);
				HSP.setHiCard(this.getCards().get(CRC.get(0).getiCardPosition()));
				HSP.setLoCard(this.getCards().get(CRC.get(1).getiCardPosition()));
				ArrayList<Card> kickers = new ArrayList<Card>();
				HSP.setKickers(kickers);
				this.setHS(HSP);
			}
		}
		return bisFullHouse;

	}

	public boolean isFlush() {
		boolean bisFlush = false;

		int iCardCnt = super.getCards().size();
		int iSuitCnt = 0;

		for (eSuit eSuit : eSuit.values()) {
			for (Card c : super.getCards()) {
				if (eSuit == c.geteSuit()) {
					iSuitCnt++;
				}
			}
			if (iSuitCnt > 0)
				break;
		}

		if (iSuitCnt == iCardCnt)
			bisFlush = true;
		else
			bisFlush = false;
		
		if (bisFlush) {
			HandScorePoker HSP = (HandScorePoker) this.getHS();
			HSP.seteHandStrength(eHandStrength.Flush);
			int iGetCard = this.getCRC().get(0).getiCardPosition();
			HSP.setHiCard(this.getCards().get(iGetCard));
			HSP.setLoCard(null);
			HSP.setKickers(FindTheKickers(this.getCRC()));
			this.setHS(HSP);
		}

		return bisFlush;
	}

	public boolean isStraight() {
		boolean bisStraight = false;
		
		int a = 0;
		if (super.getCards().get(0).geteRank() == eRank.ACE) {
			a = 1;
		}
		
		while (a < super.getCards().size() - 1) {
			if(super.getCards().get(a).geteRank().getiRankNbr()-1 == super.getCards().get(a+1).geteRank().getiRankNbr()) {
				bisStraight = true;
			}
			a++;
		}
		if (bisStraight) {
			HandScorePoker HSP = (HandScorePoker) this.getHS();
			HSP.seteHandStrength(eHandStrength.Straight);
			int iGetCard = this.getCRC().get(0).getiCardPosition();
			HSP.setHiCard(this.getCards().get(iGetCard));
			HSP.setLoCard(null);
			HSP.setKickers(FindTheKickers(this.getCRC()));
			this.setHS(HSP);
		}
		
		if ((super.getCards().get(1).geteRank() != eRank.FIVE && super.getCards().get(0).geteRank() == eRank.ACE)) {
			bisStraight = false;
		}
		
		return bisStraight;
	}

	// This is how to implement one of the 'counting' hand types. Testing to see if
	// there are three of the same rank.
	public boolean isThreeOfAKind() {
		boolean bisThreeOfAKind = false;
		if (this.getCRC().size() == 3) {
			if (this.getCRC().get(0).getiCnt() == Constants.THREE_OF_A_KIND) {
				bisThreeOfAKind = true;
				HandScorePoker HSP = (HandScorePoker) this.getHS();
				HSP.seteHandStrength(eHandStrength.ThreeOfAKind);
				int iGetCard = this.getCRC().get(0).getiCardPosition();
				HSP.setHiCard(this.getCards().get(iGetCard));
				HSP.setLoCard(null);
				HSP.setKickers(FindTheKickers(this.getCRC()));
				this.setHS(HSP);
			}
		}
		return bisThreeOfAKind;
	}

	public boolean isTwoPair() {
		boolean bisTwoPair = false;
		if (this.getCRC().size() == 3) {
			if (this.getCRC().get(0).getiCnt() == Constants.TWO_OF_A_KIND && this.getCRC().get(1).getiCnt() == Constants.TWO_OF_A_KIND) {
				bisTwoPair = true;
				HandScorePoker HSP = (HandScorePoker) this.getHS();
				HSP.seteHandStrength(eHandStrength.TwoPair);
				int iGetCard = this.getCRC().get(0).getiCardPosition();
				HSP.setHiCard(this.getCards().get(iGetCard));
				HSP.setLoCard(null);
				HSP.setKickers(FindTheKickers(this.getCRC()));
				this.setHS(HSP);
			}
		}
		
		// TODO : Implement this method
		return bisTwoPair;
	}

	public boolean isPair() {
		boolean bisPair = false;
		if (this.getCRC().size() == 4) {
			if (this.getCRC().get(0).getiCnt() == Constants.TWO_OF_A_KIND) {
				bisPair = true;
				HandScorePoker HSP = (HandScorePoker) this.getHS();
				HSP.seteHandStrength(eHandStrength.Pair);
				int iGetCard = this.getCRC().get(0).getiCardPosition();
				HSP.setHiCard(this.getCards().get(iGetCard));
				HSP.setLoCard(null);
				HSP.setKickers(FindTheKickers(this.getCRC()));
				this.setHS(HSP);
			}
		}
		return bisPair;
	}

	public boolean isHighCard() {
		boolean bisHighCard = false;
		
		if (this.getCRC().size() == 5) {
			bisHighCard = true;
			HandScorePoker HSP = (HandScorePoker) this.getHS();
			HSP.seteHandStrength(eHandStrength.HighCard);
			int iGetCard = this.getCRC().get(0).getiCardPosition();
			HSP.setHiCard(this.getCards().get(iGetCard));
			HSP.setLoCard(null);
			HSP.setKickers(FindTheKickers(this.getCRC()));
			this.setHS(HSP);
		/*if (isPair() != true && isTwoPair() != true && isThreeOfAKind() != true && isStraight() != true &&
				isFlush() != true && isFullHouse() != true && isFourOfAKind() != true && isStraightFlush() != true &&
				isRoyalFlush() != true) {
			bisHighCard = true;*/
		}
		
		return bisHighCard;
	}

	private ArrayList<Card> FindTheKickers(ArrayList<CardRankCount> CRC) {
		ArrayList<Card> kickers = new ArrayList<Card>();

		for (CardRankCount crcCheck : CRC) {
			if (crcCheck.getiCnt() == 1) {
				kickers.add(this.getCards().get(crcCheck.getiCardPosition()));
			}
		}

		return kickers;
	}

	private void Frequency() {
		CRC = new ArrayList<CardRankCount>();
		int iCnt = 0;
		int iPos = 0;
		for (eRank eRank : eRank.values()) {
			iCnt = (CountRank(eRank));
			if (iCnt > 0) {
				iPos = FindCardRank(eRank);
				CRC.add(new CardRankCount(eRank, iCnt, iPos));
			}
		}
		Collections.sort(CRC);
	}

	private int CountRank(eRank eRank) {
		int iCnt = 0;
		for (Card c : super.getCards()) {
			if (c.geteRank() == eRank) {
				iCnt++;
			}
		}
		return iCnt;
	}

	private int FindCardRank(eRank eRank) {
		int iPos = 0;

		for (iPos = 0; iPos < super.getCards().size(); iPos++) {
			if (super.getCards().get(iPos).geteRank() == eRank) {
				break;
			}
		}
		return iPos;
	}

}
