import { useNavigate } from "react-router-dom";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow,
} from "@mui/material";
import {
  PostCodeResponse,
  SuburbResponse,
} from "../../services/api-responses.interfaces";
import ListItm from "../../components/ListItm/ListItm";

// define the props
interface ListContainerProps {
  postcodes: PostCodeResponse[];
  setPostcodeId: (postcodeId: number | undefined) => void;
  setOpenModal: (openModal: boolean) => void;
}

const ListContainer = ({
  postcodes,
  setPostcodeId,
  setOpenModal,
}: ListContainerProps) => {
  const navigate = useNavigate();

  const deleteOnClick = (id: number | undefined) => {
    if (id !== undefined) {
      setPostcodeId(id);
      setOpenModal(true);
    } else {
      console.error("Id is undefined for deleting a postcode by id");
      throw new Error(`Unable to find Postcode with: ${id}`);
    }
  };

  const handleEdit = (id: number | undefined) => {
    if (id !== undefined) {
      navigate(`/postcodes/${id}/edit`);
    } else {
      console.error(`Unable to find postcode with id: ${id}`);
      throw new Error(`Unable to update this postcode. Please try again`);
    }
  };

  return (
    <Table sx={{ width: "100%" }}>
      <TableHead>
        <TableRow>
          <TableCell style={{ fontWeight: "bold" }}>Postcode</TableCell>
          <TableCell style={{ fontWeight: "bold" }}>Suburb</TableCell>
          <TableCell></TableCell>
        </TableRow>
      </TableHead>
      <TableBody>
        {postcodes.map((postcode: PostCodeResponse) =>
          postcode.associatedSuburbs.length > 0 ? (
            postcode.associatedSuburbs.map(
              (suburb: SuburbResponse, index: number) => (
                <TableRow key={`${postcode.id}-${index}`}>
                  <ListItm
                    id={postcode.id}
                    postcode={postcode.postcode}
                    suburbName={suburb.name}
                    suburbState={suburb.state}
                    deleteOnClick={deleteOnClick}
                    handleEdit={handleEdit}
                  />
                </TableRow>
              )
            )
          ) : (
            // handle where postcodes don't have suburbs
            <TableRow key={postcode.id}>
              <ListItm
                id={postcode.id}
                postcode={postcode.postcode}
                suburbName="No associated suburbs"
                suburbState=""
                deleteOnClick={deleteOnClick}
                handleEdit={handleEdit}
              />
            </TableRow>
          )
        )}
      </TableBody>
    </Table>
  );
};

export default ListContainer;
