package selenium.aaold.cases.community.testcase;

import lombok.Builder;

@Builder
public class FriendshipTest {
/*
    public FriendshipTest testAcceptFriendRequest() {
        SeleniumAccount account1 = seleniumAccountSupplier.get();
        SeleniumAccount account2 = seleniumAccountSupplier.get();

        goToCommunityPageOf(account1, account1.getCharacter1());

        sendFriendRequestTo(account2.getCharacter1());

        goToCommunityPageOf(account2, account2.getCharacter1(), 1);

        verifyFriendRequestArrived(account1.getCharacter1());
        List<SeleniumFriendRequest> friendRequests = communityPage.getFriendRequests();
        friendRequests.stream()
            .filter(seleniumFriendRequest -> seleniumFriendRequest.getCharacterName().equals(account1.getCharacter1().getCharacterName()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("FriendRequest not found."))
            .accept();

        assertFalse(
            communityPage.getFriendRequests().stream()
                .anyMatch(seleniumFriendRequest -> seleniumFriendRequest.getCharacterName().equals(account1.getCharacter1().getCharacterName()))
        );

        communityPage.getFriendsPageButton().click();
        verifyFriendInList(account1.getCharacter1());

        goToCommunityPageOf(account1, account1.getCharacter1());

        verifyFriendInList(account2.getCharacter1());

        communityPage.getSentFriendRequestsPageButton().click();
        assertFalse(
            communityPage.getSentFriendRequests().stream()
                .anyMatch(sentFriendRequest -> sentFriendRequest.getCharacterName().equals(account2.getCharacter1().getCharacterName()))
        );

        return this;
    }

    private void verifyFriendRequestNotifications(int numberOfWantedNotifications) {
        assertEquals(numberOfWantedNotifications, communityPage.getNumberOfFriendRequests());
    }

    private void verifyFriendInList(SeleniumCharacter character) {
        assertTrue(
            communityPage.getFriends().stream()
            .anyMatch(friend -> friend.getCharacterName().equals(character.getCharacterName()))
        );
    }*/

}
